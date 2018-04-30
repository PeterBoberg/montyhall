package se.peterboberg.montyhall.simulation;

import org.springframework.stereotype.Component;
import se.peterboberg.montyhall.utils.StringConstants;
import se.peterboberg.montyhall.game.MontyHallGame;
import se.peterboberg.montyhall.game.MontyHallGameImpl;
import se.peterboberg.montyhall.model.Box;
import se.peterboberg.montyhall.model.BoxNumber;
import se.peterboberg.montyhall.model.SimulationResult;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class MontyHallSimulationImpl implements MontyHallSimulation {

    private Delegate delegate;
    private ExecutorService executorService;
    private boolean isRunningSimulation = false;

    @Override
    synchronized public void setDelegate(Delegate delegate) {
        this.delegate = delegate;
    }

    @Override
    synchronized public void start(final BigInteger numberOfSimulations) {
        if (delegate == null)
            throw new IllegalStateException(StringConstants.ErrorTexts.NO_DELEGATE);

        if (!isRunningSimulation) {
            isRunningSimulation = true;
            executorService = Executors.newSingleThreadExecutor();
            executorService.submit(() -> runSimulations(numberOfSimulations));
        } else
            throw new IllegalStateException(StringConstants.ErrorTexts.SIMULATION_ALREADY_RUNNING);
    }

    @Override
    synchronized public void stop() {
        this.executorService.shutdownNow();
        this.isRunningSimulation = false;
    }

    // Executes on thread-pool thread
    private void runSimulations(BigInteger numberOfSimulations) {
        SimulationResult accumulativeResult = new SimulationResult();
        for (BigInteger i = BigInteger.ZERO; i.compareTo(numberOfSimulations) < 0; i = i.add(BigInteger.ONE)) {
            if (threadNotInterrupted()) {
                runNewSimulation(i, accumulativeResult);
                notifyDelegateIfNeeded(i, numberOfSimulations);
            }
        }
        delegate.onSimulationDone(accumulativeResult);
        this.isRunningSimulation = false;
    }

    // Executes on thread-pool thread
    private void runNewSimulation(BigInteger simulationRound, SimulationResult accumulativeResult) {
        BoxNumber selectedBoxNumber = MontyHallGame.randomBoxNumber();
        MontyHallGame montyHallGame = new MontyHallGameImpl();

        montyHallGame.selectBoxAndReceiveOffer(selectedBoxNumber, (Box selectedBox, Box alternateBox) -> {
            // Switch between keeping first selected box and alternate box every second simulation
            boolean shouldSelectAlternateBox = simulationRound.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO);
            Box chosenBox = shouldSelectAlternateBox ? alternateBox : selectedBox;

            if (chosenBox.isMoneyInside()) {
                if (shouldSelectAlternateBox)
                    accumulativeResult.incrementWinsBySelectingAlternateBox();
                else
                    accumulativeResult.incrementWinsBySelectingFirstBox();
            }

            if (shouldSelectAlternateBox)
                accumulativeResult.incrementSimulationsSelectingAlternateBox();
            else
                accumulativeResult.incrementSimulationsSelectingFirstBox();

            accumulativeResult.incrementSimulationsDone();
        });
    }

    // Executes on thread-pool thread
    private void notifyDelegateIfNeeded(BigInteger index, BigInteger numberOfSimulations) {
        if (delegate != null) {
            BigInteger modulo = numberOfSimulations.divide(new BigInteger("10"));
            if (index.mod(modulo).equals(BigInteger.ZERO)) {
                BigDecimal soFar = new BigDecimal(index);
                BigDecimal total = new BigDecimal(numberOfSimulations);
                double progress = soFar.divide(total).doubleValue();
                delegate.onSimulationUpdateProgress(progress);
            }
        }
    }

    // Executes on thread-pool thread
    private boolean threadNotInterrupted() {
        return !Thread.currentThread().isInterrupted();
    }
}
