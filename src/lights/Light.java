/**
 *  2019 - Universidad Panamericana 
 *  All Rights Reserved
 */
package src.lights;

import src.Intersection;
import src.Ray;
import src.Vector3D;
import src.objects.Object3D;
import src.tools.MaterialObject3D;

/**
 *
 * @author Jafet & Felipe
 */
public abstract class Light extends Object3D {
    private double intensity;
    private boolean shadow;

    /*
     * Obtains: position: Vector3D, material: MaterialObject3D, intensity: double,
     * shadow boolean Description: Constructor of Light Class
     */
    public Light(Vector3D position, MaterialObject3D material, double intensity, boolean shadow) {
        super(position, material);
        setIntensity(intensity);
        setShadow(shadow);
    }

    // @Override
    public Intersection getIntersection(Ray ray) {
        return new Intersection(Vector3D.ZERO(), -1, Vector3D.ZERO(), null);
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    public boolean getShadow() {
        return shadow;
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }

    public abstract float getNDotL(Intersection intersection);
}
