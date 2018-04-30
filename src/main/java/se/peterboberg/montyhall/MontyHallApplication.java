package se.peterboberg.montyhall;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import se.peterboberg.montyhall.utils.StringConstants;

@SpringBootApplication
public class MontyHallApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationContext context = SpringApplication.run(MontyHallApplication.class);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(StringConstants.Paths.MAIN_VIEW_PATH));
        loader.setControllerFactory(context::getBean);
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();
    }
}
