package homework1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Art extends Application {

    private double width = 1000;
    private double height = 650;

    private double time = 0;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Sea");

        Group root = new Group();
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);
        Scene scene = new Scene(root, width, height);

        drawFrame(gc);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(33), e -> {
            time += 0.033;
            drawFrame(gc);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        stage.setScene(scene);
        stage.show();
    }

    private void drawFrame(GraphicsContext gc) {
        gc.clearRect(0, 0, width, height);

        drawSky(gc);
        drawSun(gc);
        drawWater(gc);
        drawShip(gc);  // السفينة الكبيرة
        drawBirds(gc);
    }

    private void drawSky(GraphicsContext gc) {
        LinearGradient lg = new LinearGradient(
                0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#0f2b57")),
                new Stop(0.4, Color.web("#2b5fa8")),
                new Stop(1, Color.web("#8fbbe6"))
        );
        gc.setFill(lg);
        gc.fillRect(0, 0, width, height * 0.55);

        gc.setGlobalAlpha(0.55);
        for (int i = 0; i < 60; i++) {
            double sx = (i * 73.1 + 31.7) % width;
            double sy = (i * 37.3 + 19.4) % (height * 0.3);
            double r = 0.8 + (i % 3) * 0.6;
            gc.setFill(Color.rgb(255, 255, 255, 0.7 - 0.01 * i));
            gc.fillOval(sx, sy, r, r);
        }
        gc.setGlobalAlpha(1.0);

        gc.setFill(Color.rgb(255, 240, 210, 0.08));
        gc.fillRect(0, height * 0.42, width, height * 0.13);
    }

    private void drawSun(GraphicsContext gc) {
        double sunX = width * 0.78;
        double sunY = height * 0.25;
        double r = 60;

        for (int i = 8; i >= 1; i--) {
            double a = 0.03 * i;
            gc.setFill(Color.rgb(255, 200, 90, a));
            gc.fillOval(
                    sunX - r * (1 + 0.15 * i),
                    sunY - r * (1 + 0.15 * i),
                    r * 2 * (1 + 0.15 * i),
                    r * 2 * (1 + 0.15 * i)
            );
        }

        gc.setFill(Color.web("#ffd27f"));
        gc.fillOval(sunX - r, sunY - r, r * 2, r * 2);

        gc.setGlobalAlpha(0.28);
        gc.setFill(Color.web("#ffd27f"));
        gc.fillOval(sunX - r * 0.9, height * 0.68 - r * 0.2, r * 1.8, r * 0.8);
        gc.setGlobalAlpha(1.0);
    }

    private void drawWater(GraphicsContext gc) {
        double top = height * 0.55;
        gc.setFill(Color.web("#0f3a57"));
        gc.fillRect(0, top, width, height - top);

        LinearGradient waterG = new LinearGradient(
                0, top / height, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#164a64", 0.8)),
                new Stop(1, Color.web("#071a25", 0.95))
        );
        gc.setFill(waterG);
        gc.fillRect(0, top, width, height - top);

        gc.setGlobalAlpha(0.18);
        gc.setStroke(Color.web("#ffffff"));
        gc.setLineWidth(1.2);
        int lines = 9;
        for (int l = 0; l < lines; l++) {
            double y = top + 20 + l * 18;
            gc.beginPath();
            for (int x = 0; x <= width; x += 4) {
                double wave = Math.sin((x * 0.02) + time * (0.8 + l * 0.05) + l) * (6 + l * 0.9);
                double yy = y + wave * Math.exp(-l * 0.12);
                if (x == 0) gc.moveTo(x, yy);
                else gc.lineTo(x, yy);
            }
            gc.stroke();
        }
        gc.setGlobalAlpha(1.0);

        gc.setGlobalAlpha(0.12);
        gc.setFill(Color.web("#ffd27f"));
        gc.fillRect(width * 0.72, top + 40, 200, 80);
        gc.setGlobalAlpha(1.0);
    }

    private void drawBirds(GraphicsContext gc) {
        int birds = 4;
        for (int i = 0; i < birds; i++) {
            double bx = (time * 120 + i * 160) % (width + 200) - 100;
            double by = height * 0.22 + Math.sin(time * 1.2 + i) * 18 + i * 6;
            drawBird(gc, bx, by, 18 - i * 2);
        }
    }

    private void drawBird(GraphicsContext gc, double x, double y, double s) {
        gc.setStroke(Color.web("#111111"));
        gc.setLineWidth(2);
        gc.beginPath();
        gc.moveTo(x - s * 0.6, y);
        gc.quadraticCurveTo(x - s * 0.1, y - s * 0.5, x + s * 0.4, y);
        gc.moveTo(x - s * 0.2, y);
        gc.quadraticCurveTo(x + s * 0.2, y - s * 0.45, x + s * 0.8, y);
        gc.stroke();
        gc.closePath();
    }

    // دالة لرسم السفينة الكبيرة مثل التايتانك
    private void drawShip(GraphicsContext gc) {
        double shipWidth = 300;   // طول كبير
        double shipHeight = 60;   // ارتفاع كبير
        double shipX = width * 0.3 + Math.sin(time * 0.5) * 50; // تتحرك مع الموج
        double shipY = height * 0.6;

        // جسم السفينة الأساسي (قاعدة السفينة)
        gc.setFill(Color.DARKRED);
        gc.fillPolygon(
            new double[]{shipX, shipX + shipWidth, shipX + shipWidth - 30, shipX + 30},
            new double[]{shipY, shipY, shipY + shipHeight, shipY + shipHeight},
            4
        );

        

        // الصواري (3 صواري)
        gc.setStroke(Color.BROWN);
        gc.setLineWidth(4);
        double[] mastX = {
       
            shipX + shipWidth * 0.75
        };
        double mastTop = shipY - 70;
        for (double mx : mastX) {
            gc.strokeLine(mx, shipY - 5, mx, mastTop);
        }

        // الأعلام الصغيرة على كل صاري
        gc.setFill(Color.WHITE);
        for (double mx : mastX) {
            gc.fillPolygon(
                new double[]{mx, mx, mx + 25},
                new double[]{mastTop, mastTop + 15, mastTop + 7},
                3
            );
        }

        // ظل السفينة على الماء
        gc.setGlobalAlpha(0.25);
        gc.setFill(Color.BLACK);
        gc.fillOval(shipX + 20, shipY + shipHeight - 5, shipWidth - 40, 20);
        gc.setGlobalAlpha(1.0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
