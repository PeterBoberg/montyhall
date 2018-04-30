package se.peterboberg.montyhall.model;

import java.math.BigInteger;

public class SimulationResult {

    private BigInteger winsBySelectingFirstBox = BigInteger.ZERO;
    private BigInteger winsBySelectingAlternateBox = BigInteger.ZERO;
    private BigInteger simulationsDone = BigInteger.ZERO;
    private BigInteger simulationsSelectingFirstBox = BigInteger.ZERO;
    private BigInteger simulationsSelectingAlternateBox = BigInteger.ZERO;


    public BigInteger getWinsBySelectingFirstBox() {
        return winsBySelectingFirstBox;
    }

    public BigInteger getWinsBySelectingAlternateBox() {
        return winsBySelectingAlternateBox;
    }

    public BigInteger getSimulationsDone() {
        return simulationsDone;
    }

    public BigInteger getSimulationsSelectingFirstBox() {
        return simulationsSelectingFirstBox;
    }

    public BigInteger getSimulationsSelectingAlternateBox() {
        return simulationsSelectingAlternateBox;
    }

    public void incrementWinsBySelectingFirstBox() {
        winsBySelectingFirstBox = winsBySelectingFirstBox.add(BigInteger.ONE);
    }

    public void incrementWinsBySelectingAlternateBox() {
        winsBySelectingAlternateBox = winsBySelectingAlternateBox.add(BigInteger.ONE);
    }

    public void incrementSimulationsDone() {
        simulationsDone = simulationsDone.add(BigInteger.ONE);
    }

    public void incrementSimulationsSelectingFirstBox() {
        simulationsSelectingFirstBox = simulationsSelectingFirstBox.add(BigInteger.ONE);
    }

    public void incrementSimulationsSelectingAlternateBox() {
        simulationsSelectingAlternateBox = simulationsSelectingAlternateBox.add(BigInteger.ONE);
    }
}
