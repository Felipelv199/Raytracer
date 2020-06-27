/**
 *  2019 - Universidad Panamericana 
 *  All Rights Reserved
 */
package src;

import src.lights.Light;
import src.objects.Camera;
import src.objects.Object3D;
import java.util.ArrayList;

/**
 *
 * @author Jafet
 */
public class Scene {
    private Camera camera;
    private ArrayList<Light> lights;
    private ArrayList<Object3D> objects;

    /*
     * Description: Constructor of Scene Class
     */
    public Scene() {
        setObjects(new ArrayList<Object3D>());
        setLights(new ArrayList<Light>());
    }

    public void addObject(Object3D object) {
        getObjects().add(object);
    }

    public ArrayList<Object3D> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<Object3D> objects) {
        this.objects = objects;
    }

    public void addLight(Light light) {
        getLights().add(light);
    }

    public ArrayList<Light> getLights() {
        return lights;
    }

    public void setLights(ArrayList<Light> lights) {
        this.lights = lights;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

}
