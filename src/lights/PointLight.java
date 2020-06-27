/**
 *  2019 - Universidad Panamericana 
 *  All Rights Reserved
 */
package src.lights;

import src.Intersection;
import src.Vector3D;
import src.tools.MaterialObject3D;

/**
 *
 * @author Jafet & Felipe
 */
public class PointLight extends Light {
    private Vector3D direction;

    /*
     * Obtains: position: Vector3D, direction: Vector3D, material: MateiralObject3D,
     * intensity: double, shadow: boolean Description: Constructor of PointLight
     * Class
     */
    public PointLight(Vector3D position, Vector3D direction, MaterialObject3D material, double intensity,
            boolean shadow) {
        super(position, material, intensity, shadow);
        setDirection(Vector3D.normalize(Vector3D.substract(position, direction)));
    }

    public Vector3D getDirection() {
        return direction;
    }

    public void setDirection(Vector3D direction) {
        this.direction = direction;
    }

    /*
     * Obtains: intersection: Intersection Description: Calculates the new intensity
     * for the PointLight Returns: i: double
     */
    public double intensityCalculation(Intersection intersection) {
        double i = super.getIntensity() * getNDotL(intersection);
        double distance = intersection.getDistance();
        i = i / (distance * distance);
        return i;
    }

    public float getNDotL(Intersection intersection) {
        return (float) Math.max(
                Vector3D.dotProduct(intersection.getNormal(), Vector3D.scalarMultiplication(getDirection(), 1.0)), 0.0);
    }
}
