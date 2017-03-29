package Model; /**
 * Created by Seth on 3/22/2017.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Search extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Concert Finder");

        TextField textField = new TextField();
        textField.setPrefWidth(360);



        Button button = new Button("Enter Band Name");

        button.setOnAction(action -> {
            String aConcert = textField.getText();
            System.out.println(aConcert +" will be peforming tonight at 8:00pm.");
            System.out.println("Location: 355, College View Avenue.");
        });

        HBox hbox = new HBox(textField, button);

        Scene scene = new Scene(hbox, 500, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
