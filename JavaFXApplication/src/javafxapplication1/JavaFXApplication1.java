/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

/**
 *
 * @author Seazic1
 */
public class JavaFXApplication1 extends Application {
//    
//    @Override
//    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
//        System.out.println("javafxapplication1.JavaFXApplication1.start()");
//        System.out.println("Host : "+this.getHostServices().getCodeBase());
//        Scene scene = new Scene(root);
//        
//        stage.setScene(scene);
//        stage.show();
//    }

    private double anchorX;
    private double anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);
 
    @Override
    public void start(Stage primaryStage) {
 
//        final MeshView myMesh = new MeshView(new MyMesh(200f, (float) (100 * Math.sqrt(3.0d)), 0f));
 
        final MeshView myMesh = new MeshView(new RandMap3D(-300,-300, 300, 300));
 
        if(getClass().getResource("resources/ou.png") != null)
            System.out.println(" mmmmmm");
        Image diffuseMap = new Image(getClass().getResource("resources/spec.png").toString());
 
        PhongMaterial material = new PhongMaterial();
 
        material.setDiffuseMap(diffuseMap);
 
        myMesh.setMaterial(material);
 
        // 環境光設定
        final AmbientLight ambient = new AmbientLight();
        ambient.setColor(Color.rgb(90, 90, 90, 0.6));
 
        // ポイントライト設定
        PointLight point = new PointLight();
        point.setColor(Color.WHITE);
        point.setLayoutX(-600.0d);
        point.setLayoutY(-200.0d);
        point.setTranslateZ(-400.0d);
 
        PointLight point2 = new PointLight();
        point2.setColor(Color.SKYBLUE);
        point2.setLayoutX(600.0d);
        point2.setLayoutY(-200.0d);
        point2.setTranslateZ(-400.0d);
 
        PerspectiveCamera camera = new PerspectiveCamera(true);
 
        // Field of View 
        camera.setFieldOfView(45.5d);
        // Clipping Planes
        camera.setNearClip(1.0d);
        camera.setFarClip(1_000_000.0d);
 
        camera.setTranslateZ(-800);
 
        final Group root = new Group(myMesh);
 
        Rotate xRotate;
        Rotate yRotate;
        myMesh.getTransforms().setAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);
 
        root.getChildren().addAll(ambient, point, point2);
 
        Scene scene = new Scene(root, 1_024, 768, true, SceneAntialiasing.BALANCED);
 
        scene.setFill(Color.ALICEBLUE);
 
        scene.setCamera(camera);
 
        scene.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });
 
        scene.setOnMouseDragged(event -> {
            angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
            angleY.set(anchorAngleY + anchorX - event.getSceneX());
        });
 
        primaryStage.setTitle("MyFirstMesh");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
 
    private class MyMesh extends TriangleMesh {
 
        MyMesh(float w, float h, float d) {
 
            this.getPoints().addAll(
                    0, 0, 0,      // Top
                    -w / 2, h, 0, // Bottom Left
                    w / 2, h, 0   // Bottom Right                   
            );
 
            this.getTexCoords().addAll(
                    1 / 2f, 0,                   // idx 0
                    0, (float) Math.sqrt(3) / 2, // idx 1
                    1, (float) Math.sqrt(3) / 2  // idx 2
            );
 
            this.getFaces().addAll(
                    0, 0, 1, 1, 2, 2,
                    2, 2, 1, 1, 0, 0
            );
 
        }
 
    }
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        System.out.println("javafxapplication1.JavaFXApplication1.main()");
//        launch(args);
//    }
    
}
