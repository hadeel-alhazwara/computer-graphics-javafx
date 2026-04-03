package homework1;

// استيراد مكتبات JavaFX اللازمة
import javafx.application.Application;      // لتشغيل تطبيق JavaFX
import javafx.scene.Scene;                 // لتمثيل المشهد الذي يعرض العناصر
import javafx.scene.canvas.Canvas;         // لإنشاء لوحة رسم
import javafx.scene.canvas.GraphicsContext;// للتحكم بالرسم على الـ Canvas
import javafx.scene.paint.*;               // للألوان والتدرجات اللونية
import javafx.scene.Group;                 // لتجميع العناصر داخل المشهد
import javafx.stage.Stage;                 // لتمثيل النافذة الأساسية
import javafx.animation.AnimationTimer;    // لإنشاء حركة متكررة (Frames per second)

public class Sea_art1 extends Application {

    private double time = 0; 

    @Override
    public void start(Stage stage) {
        
        Canvas canvas = new Canvas(900, 600);
       
        GraphicsContext gc = canvas.getGraphicsContext2D();

       
        Group root = new Group(canvas);
      
        Scene scene = new Scene(root);

        
        stage.setScene(scene);
      
        stage.setTitle("Sea with Ship ");
     
        stage.show();

        
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                time += 0.02;      
                drawArt(gc);       
            }
        }.start();
    }

 
    private void drawArt(GraphicsContext gc) {

        
        LinearGradient sky = new LinearGradient(
                0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop[]{
                        new Stop(0, Color.web("#0A1A3A")), 
                        new Stop(0.3, Color.web("#324678")),
                        new Stop(0.6, Color.web("#E47A45")),
                        new Stop(1, Color.web("#F3CA76"))  
                }
        );
        gc.setFill(sky);               
        gc.fillRect(0, 0, 900, 600);   

        //-------------------- الشمس --------------------
        gc.setFill(Color.web("#FFD35F"));  
        gc.fillOval(350, 160, 200, 200);   

        //-------------------- جبل --------------------
        gc.setFill(Color.PERU);   
        gc.fillPolygon(
                new double[]{80, 260, 420}, 
                new double[]{430, 210, 430}, 
                3                           
        );

        //-------------------- البحر مع تموجات --------------------
        drawWater(gc);

        //-------------------- انعكاس الشمس على البحر --------------------
        drawSunReflection(gc);

        //-------------------- جزيرة --------------------
        gc.setFill(Color.web("#D9C59B")); 
        gc.fillOval(350, 350, 200, 60);  

        //-------------------- نخلة 1 --------------------
        gc.setStroke(Color.web("#0E121A"));
        gc.setLineWidth(7);                
        gc.strokeLine(420, 310, 420, 360);
        drawPalm(gc, 420, 310);          

        //-------------------- نخلة 2 --------------------
        gc.setLineWidth(5);
        gc.strokeLine(480, 320, 480, 365);
        drawPalm(gc, 480, 320);

        //-------------------- الطيور  --------------------
        drawBirdsMoving(gc); 

        //-------------------- السفينة مع الراية --------------------
        drawTitanicShip(gc, (time * 40) % 900, 400); 
    }

 
    private void drawWater(GraphicsContext gc) {
        double top = 350;

   
        LinearGradient water = new LinearGradient(
                0, 0.5, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop[]{
                        new Stop(0, Color.web("#1C2D49")), 
                        new Stop(1, Color.web("#0A162A"))  
                }
        );
        gc.setFill(water);
        gc.fillRect(0, top, 900, 250); 

        //  التموجات على سطح البحر
        gc.setStroke(Color.web("#ffffff", 0.2)); 
        gc.setLineWidth(2);                    
        for (int i = 0; i < 5; i++) {
            double yOffset = top + i * 15;       
            gc.beginPath();
            for (int x = 0; x <= 900; x += 1) {
                double wave = Math.sin((x * 0.02) + (time + i)) * (5 + i * 1.5);
                if (x == 0) gc.moveTo(x, yOffset + wave);
                else gc.lineTo(x, yOffset + wave);
            }
            gc.stroke(); // رسم التموج
        }
    }

    //-------------------- انعكاس الشمس على الماء --------------------
    private void drawSunReflection(GraphicsContext gc) {
        double top = 350;
                   
        gc.setFill(Color.web("#FFD35F"));   
        gc.fillOval(320, top + 20, 260, 60); 
        gc.setGlobalAlpha(1.0);              
    }

    //--------------------  أوراق النخيل --------------------
    private void drawPalm(GraphicsContext gc, double x, double y) {
        gc.setStroke(Color.web("#1F2937")); 
        gc.setLineWidth(4);
        gc.strokeLine(x, y, x + 25, y - 15);
        gc.strokeLine(x, y, x - 25, y - 15);
        gc.strokeLine(x, y, x + 30, y + 5);
        gc.strokeLine(x, y, x - 30, y + 5);
        gc.strokeLine(x, y, x, y - 22);     
    }

    //-------------------- رسم طائر   --------------------
    private void drawBird(GraphicsContext gc, double x, double y, int i) {
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2 + i*0.2);
        gc.beginPath();
        gc.moveTo(x, y);
        gc.quadraticCurveTo(x + 10, y - 10, x + 20, y); 
        gc.moveTo(x + 5, y + 2);
        gc.quadraticCurveTo(x + 15, y - 8, x + 25, y + 2); 
        gc.stroke();
        gc.closePath();
    }

    //-------------------- رسم الطيور المتحركة --------------------
    private void drawBirdsMoving(GraphicsContext gc) {
        int birds = 6;
        for (int i = 0; i < birds; i++) {
           
            double bx = 150 + ((time * 50 + i * 100) % 600); 
           
            double by = 120 + Math.sin(time * 2 + i) * 20;   
            drawBird(gc, bx, by, i);
        }
    }

    //--------------------  السفينة مع راية --------------------
    private void drawTitanicShip(GraphicsContext gc, double x, double y) {
      
        gc.setFill(Color.web("#8B4513")); // بني غامق
        gc.fillPolygon(
                new double[]{x, x + 200, x + 180, x + 20},
                new double[]{y, y, y + 40, y + 40},
                4
        );

        // السطح العلوي
        gc.setFill(Color.web("#D2691E")); // برتقالي غامق
        gc.fillRect(x + 40, y - 20, 120, 20);

        // السارية
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);
        gc.strokeLine(x + 100, y - 20, x + 100, y - 50);

        // الراية
        gc.setFill(Color.RED);
        gc.fillPolygon(
                new double[]{x + 100, x + 100, x + 120},
                new double[]{y - 50, y - 40, y - 45},
                3
        );
    }

  
    public static void main(String[] args) {
        launch(args); 
    }
}
