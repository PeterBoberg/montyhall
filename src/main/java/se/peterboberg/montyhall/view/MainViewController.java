package se.peterboberg.montyhall.view;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.peterboberg.montyhall.utils.StringConstants;
import se.peterboberg.montyhall.model.SimulationResult;
import se.peterboberg.montyhall.simulation.MontyHallSimulation;
import se.peterboberg.montyhall.simulation.MontyHallSimulationImpl;

import java.math.BigInteger;

@Component
public class MainViewController {

    // UI Fields
    @FXML
    private Button runSimulationButton;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Text runningInfoText;

    @FXML
    private GridPane mainGridPane;

    @FXML
    private TextField amountSimulationsTextfield;

    @FXML
    private Text firstChoiceNumberOfWinsText;

    @FXML
    private Text alternateChoiceNumberOfWinsText;

    @FXML
    private Text bestChoiceText;

    @FXML
    private Text errorText;

    @FXML
    private Text timesTriedChoosingFirstText;

    @FXML
    private Text timesTriedChoosingAlternateText;

    //Injected fields
    @Autowired
    private MontyHallSimulation montyHallSimulation;

    // Private fields
    private BooleanProperty isRunning = new SimpleBooleanProperty();

    @FXML
    private void initialize() {
        isRunning.setValue(false);
        montyHallSimulation.setDelegate(getSimulationDelegate());
        runSimulationButton.textProperty().bind(Bindings.when(isRunning).then(StringConstants.STOP).otherwise(StringConstants.RUN));
        progressBar.visibleProperty().bind(isRunning);
        runningInfoText.visibleProperty().bind(isRunning);
        bestChoiceText.visibleProperty().bind(isRunning.not());
        mainGridPane.visibleProperty().bind(isRunning.not());
    }

    @FXML
    private void runStopSimulation() {
        errorText.setText(StringConstants.EMPTY_STRING);
        clearPreviousResults();

        if (!isRunning.getValue()) {
            try {
                BigInteger numberOfSimulations = new BigInteger(amountSimulationsTextfield.getText());
                runningInfoText.setText(StringConstants.textForNumberOfSimulations(numberOfSimulations.toString()));
                montyHallSimulation.start(numberOfSimulations);
                isRunning.setValue(true);
            } catch (NumberFormatException e) {
                errorText.setText(StringConstants.INVALID_INTEGER_NUMBER);
            }
        } else {
            montyHallSimulation.stop();
            isRunning.setValue(false);
        }
    }

    private void clearPreviousResults() {
        firstChoiceNumberOfWinsText.setText(StringConstants.ZERO);
        alternateChoiceNumberOfWinsText.setText(StringConstants.ZERO);
        bestChoiceText.setText(StringConstants.EMPTY_STRING);
        timesTriedChoosingAlternateText.setText(StringConstants.textForTimesTried(StringConstants.ZERO));
        timesTriedChoosingFirstText.setText(StringConstants.textForTimesTried(StringConstants.ZERO));
    }

    private void presentResult(SimulationResult result) {
        isRunning.setValue(false);
        firstChoiceNumberOfWinsText.setText(result.getWinsBySelectingFirstBox().toString());
        alternateChoiceNumberOfWinsText.setText(result.getWinsBySelectingAlternateBox().toString());
        timesTriedChoosingFirstText.setText(StringConstants.textForTimesTried(result.getSimulationsSelectingFirstBox().toString()));
        timesTriedChoosingAlternateText.setText(StringConstants.textForTimesTried(result.getSimulationsSelectingAlternateBox().toString()));
        boolean changingWasBestDecision = result.getWinsBySelectingAlternateBox().compareTo(result.getWinsBySelectingFirstBox()) > 0;
        String bestChoice = changingWasBestDecision ? StringConstants.CHANGE_YOUR_MIND : StringConstants.KEEP_FIRST_SELECTION;
        bestChoiceText.setText(StringConstants.textForBestChioce(bestChoice));
    }

    private MontyHallSimulation.Delegate getSimulationDelegate() {
        return new MontyHallSimulationImpl.Delegate() {

            @Override
            public void onSimulationUpdateProgress(double percentDone) {
                Platform.runLater(() -> progressBar.setProgress(percentDone));
            }

            @Override
            public void onSimulationDone(SimulationResult result) {
                Platform.runLater(() -> presentResult(result));
            }
        };
    }
}
