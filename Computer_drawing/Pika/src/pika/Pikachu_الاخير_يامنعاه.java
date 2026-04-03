package pika;

// استيراد المكتبات اللازمة للرسوم، الحركة، والوسائط
import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class Pikachu_الاخير_يامنعاه extends Application {

    //  لضمان استمرار تشغيله وعدم حذفه من الذاكرة
    private MediaPlayer mediaPlayer; 

    @Override
    public void start(Stage stage) {
        // Group: هي الحاوية الأساسية التي سنضع فيها كل أجزاء بيكاتشو
        Group root = new Group();

        // --- قسم تشغيل الصوت ---
        try {
            String musicFile = "Pika.mp3"; // اسم ملف الصوت
            // تحويل الملف إلى مسار URI ليتمكن المشغل من قراءته
            Media sound = new Media(new File(musicFile).toURI().toString());
            mediaPlayer = new MediaPlayer(sound);
            
            // INDEFINITE: تعني تكرار لا نهائي للصوت
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.5); // ضبط مستوى الصوت للنصف
            mediaPlayer.play(); // بدء التشغيل
        } catch (Exception e) {
            // في حال عدم وجود الملف، سيطبع هذا الخطأ ويستمر البرنامج بدون صوت
            System.out.println("تأكد من وضع ملف Pika.mp3 في مجلد المشروع الرئيسي.");
        }

        // --- قسم الألوان والتلوين ---
     
        RadialGradient bodyGrad = new RadialGradient(0, -0.1, 300, 350, 200, false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#FFF59D")),   // مركز أصفر فاتح
                new Stop(0.7, Color.web("#FDD835")),  // أصفر فاقع
                new Stop(1, Color.web("#F57F17")));   // أطراف برتقالية للظل

        Color strokeColor = Color.BLACK; // لون الحدود 
        double strokeWidth = 2.5;       // سماكة الخط الأسود

        // --- رسم أجزاء الجسم ---

        // 1. 
        Path tail = new Path(
            new MoveTo(380, 440), new LineTo(460, 440), new LineTo(420, 390),
            new LineTo(500, 390), new LineTo(450, 280), new LineTo(380, 350),
            new LineTo(420, 350), new LineTo(370, 410), new ClosePath()
        );
        // LinearGradient: تدرج خطي للذيل (بني من الأسفل وأصفر من الأعلى)
        tail.setFill(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, 
            new Stop(0, Color.web("#795548")), new Stop(0.2, Color.web("#FDD835"))));
        tail.setStroke(strokeColor);
        tail.setStrokeWidth(strokeWidth);

        // 2. الأرجل (Ellipse): أشكال بيضوية صغيرة
        Ellipse leftLeg = new Ellipse(255, 485, 28, 15);
        leftLeg.setFill(bodyGrad); leftLeg.setStroke(strokeColor); leftLeg.setStrokeWidth(strokeWidth);
        
        Ellipse rightLeg = new Ellipse(345, 485, 28, 15);
        rightLeg.setFill(bodyGrad); rightLeg.setStroke(strokeColor); rightLeg.setStrokeWidth(strokeWidth);

        // 3. الجسم (Ellipse): شكل بيضوي كبير في المنتصف
        Ellipse body = new Ellipse(300, 390, 95, 110);
        body.setFill(bodyGrad); body.setStroke(strokeColor); body.setStrokeWidth(strokeWidth);
        
        // 4. الأذرع: أشكال بيضوية مع خاصية التدوير (Rotate)
        Ellipse leftArm = new Ellipse(225, 360, 38, 14);
        leftArm.setRotate(40); // تدوير الذراع الأيسر لليمين
        leftArm.setFill(bodyGrad); leftArm.setStroke(strokeColor); leftArm.setStrokeWidth(strokeWidth);

        Ellipse rightArm = new Ellipse(375, 360, 38, 14);
        rightArm.setRotate(-40); // تدوير الذراع الأيمن لليسار
        rightArm.setFill(bodyGrad); rightArm.setStroke(strokeColor); rightArm.setStrokeWidth(strokeWidth);

        // 5. الرأس
        Ellipse head = new Ellipse(300, 275, 80, 75);
        head.setFill(bodyGrad); head.setStroke(strokeColor); head.setStrokeWidth(strokeWidth);

        // 6. الأذنان (Path + QuadCurveTo): رسم منحنيات انسيابية
        Path leftEar = new Path(new MoveTo(235, 245), new QuadCurveTo(140, 110, 190, 70), new QuadCurveTo(270, 140, 265, 235), new ClosePath());
        leftEar.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, new Stop(0, Color.BLACK), new Stop(0.35, Color.BLACK), new Stop(0.45, Color.web("#FDD835"))));
        leftEar.setStroke(strokeColor); leftEar.setStrokeWidth(strokeWidth);

        Path rightEar = new Path(new MoveTo(365, 245), new QuadCurveTo(460, 110, 410, 70), new QuadCurveTo(330, 140, 335, 235), new ClosePath());
        rightEar.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, new Stop(0, Color.BLACK), new Stop(0.35, Color.BLACK), new Stop(0.45, Color.web("#FDD835"))));
        rightEar.setStroke(strokeColor); rightEar.setStrokeWidth(strokeWidth);

        // --- ملامح الوجه ---
        // الخدود مع GaussianBlur: لإعطاء تأثير توهج ناعم (Blur)
        Circle lCheek = new Circle(230, 310, 16);
        lCheek.setFill(new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE, new Stop(0, Color.web("#FF5252")), new Stop(1, Color.web("#D32F2F"))));
        lCheek.setEffect(new GaussianBlur(4));

        Circle rCheek = new Circle(370, 310, 16);
        rCheek.setFill(new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE, new Stop(0, Color.web("#FF5252")), new Stop(1, Color.web("#D32F2F"))));
        rCheek.setEffect(new GaussianBlur(4));

        // العيون واللمعان الأبيض
        Circle lEye = new Circle(265, 265, 10, Color.web("#212121"));
        Circle rEye = new Circle(335, 265, 10, Color.web("#212121"));
        Circle lGlint = new Circle(262, 261, 3.5, Color.WHITE);
        Circle rGlint = new Circle(332, 261, 3.5, Color.WHITE);
        
        // الأنف (Polygon): مثلث صغير
        Polygon nose = new Polygon(300, 285, 298, 289, 302, 289); nose.setFill(Color.web("#212121"));
        
        // الفم (Arc): قوس يفتح ويغلق عبر الأنيميشن
        Arc mouth = new Arc(300, 312, 14, 5, 180, 180); 
        mouth.setType(ArcType.CHORD); mouth.setFill(Color.web("#880E4F"));

        // --- تجميع وترتيب الطبقات ---
        Group pika = new Group(tail, leftLeg, rightLeg, body, leftArm, rightArm, head, leftEar, rightEar, lEye, rEye, lGlint, rGlint, lCheek, rCheek, nose, mouth);
        
        // DropShadow: إضافة ظل خلف بيكاتشو ليعطيه عمقاً
        pika.setEffect(new DropShadow(25, Color.web("#000000", 0.3)));

        // --- قسم الأنيميشن (الحركة) ---
        
        // حركة الفم (Timeline): تغيير نصف القطر الرأسي للقوس ليبدو كأنه يتحدث
        Timeline sing = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(mouth.radiusYProperty(), 2)),
            new KeyFrame(Duration.millis(350), new KeyValue(mouth.radiusYProperty(), 15))
        );
        sing.setCycleCount(Timeline.INDEFINITE); 
        sing.setAutoReverse(true); // ليعود الفم للإغلاق تلقائياً
        sing.play();

        // حركة الرقص (TranslateTransition): تحريك الجسم بالكامل للأعلى والأسفل
        TranslateTransition dance = new TranslateTransition(Duration.seconds(0.6), pika);
        dance.setByY(-15); // مقدار القفز للأعلى
        dance.setCycleCount(Timeline.INDEFINITE); 
        dance.setAutoReverse(true); 
        dance.play();

        // عرض المشهد النهائي
        Scene scene = new Scene(new Group(pika), 600, 600, Color.WHITE);
        stage.setTitle("Pikachu Professional with Sound");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}