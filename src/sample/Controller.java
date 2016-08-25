package sample;

import com.sun.javafx.geom.Point2D;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable, Algorithms.Callbacks {

    private static final String EMPTY_ALGORITHM = "Algoritmo";
    private static final String DDA_ALGORITHM = "DDA";
    private static final String BRESEHAM_ALGORITHM = "Breseham";

    private static final String EMPTY_SHAPE ="Forma";
    private static final String CIRCLE_FORM = "Circunferência";
    private static final String LINE_FORM = "Reta";

    @FXML
    private Canvas mDrawingCanvas;

    @FXML
    private ChoiceBox<String> mAlgorithmChoiceBox;

    @FXML
    private ChoiceBox<String> mShapeToDrawChoiceBox;

    private Point2D start;
    private Point2D end;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mAlgorithmChoiceBox.setItems(
                FXCollections.observableArrayList(EMPTY_ALGORITHM, DDA_ALGORITHM, BRESEHAM_ALGORITHM)
        );

        mAlgorithmChoiceBox.setValue(EMPTY_ALGORITHM);

        mShapeToDrawChoiceBox.setItems(
                FXCollections.observableArrayList(EMPTY_SHAPE, CIRCLE_FORM, LINE_FORM)
        );

        mShapeToDrawChoiceBox.setValue(EMPTY_SHAPE);

        mDrawingCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if (mouseEvent.getClickCount() >= 1)
                    handleClick(mouseEvent.getX(), mouseEvent.getY());
            }
        });

    }



    @Override
    public void drawPoint(int x, int y) {
        GraphicsContext graphicsContext = mDrawingCanvas.getGraphicsContext2D();

        graphicsContext.setLineWidth(1);
        graphicsContext.setStroke(Color.PURPLE);
        graphicsContext.strokeLine(x, y, x, y);
    }

    @Override
    public void drawSimmetric(int a, int b, int xc, int yc) {

        GraphicsContext graphicsContext = mDrawingCanvas.getGraphicsContext2D();

        graphicsContext.setLineWidth(1);
        graphicsContext.setStroke(Color.PURPLE);

        graphicsContext.strokeLine(xc + a, yc + b, xc + a, yc + b);
        graphicsContext.strokeLine(xc + a, yc - b, xc + a, yc - b);
        graphicsContext.strokeLine(xc - a, yc + b, xc - a, yc + b);
        graphicsContext.strokeLine(xc - a, yc - b, xc - a, yc - b);
        graphicsContext.strokeLine(xc + b, yc + a, xc + b, yc + a);
        graphicsContext.strokeLine(xc + b, yc - a, xc + b, yc - a);
        graphicsContext.strokeLine(xc - b, yc + a, xc - b, yc + a);
        graphicsContext.strokeLine(xc - b, yc - a, xc - b, yc - a);

    }

    private void handleClick(double x, double y) {

        Point2D point = new Point2D((float) x, (float) y);

        if (start == null)
            start = point;

        else if (end == null) {
            end = point;

            runAlgorithm();
        }

        else {
            start = point;
            end = null;
        }

    }

    private void runAlgorithm() {


        if (mAlgorithmChoiceBox.getValue().equals(BRESEHAM_ALGORITHM)
                && mShapeToDrawChoiceBox.getValue().equals(CIRCLE_FORM))
            Algorithms.bresehamCircle((int)start.x, (int)start.y, (int)end.x, (int)end.y, this);

        else if (mAlgorithmChoiceBox.getValue().equals(BRESEHAM_ALGORITHM)
                && mShapeToDrawChoiceBox.getValue().equals(LINE_FORM))
            Algorithms.bresehamLine((int)start.x, (int)start.y, (int)end.x, (int)end.y, this);

        else if (mAlgorithmChoiceBox.getValue().equals(DDA_ALGORITHM)
                && mShapeToDrawChoiceBox.getValue().equals(LINE_FORM))
            Algorithms.dda((int)start.x, (int)start.y, (int)end.x, (int)end.y, this);

        else
            showErrorDialog();
    }

    private void showErrorDialog() {
        final Stage dialogStage = new Stage();

        Button okButton = new Button("Ok");
        okButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                dialogStage.close();
            }
        });

        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setScene(new Scene(VBoxBuilder.create().
                children(
                        new Text("Configuração inválida. Tente novamente com novos parâmetros!"),
                        okButton).
                alignment(Pos.CENTER).padding(new Insets(10)).build()));

        dialogStage.show();
    }
}
