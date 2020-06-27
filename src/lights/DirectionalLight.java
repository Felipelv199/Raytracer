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
public class DirectionalLight extends Light {
    private Vector3D direction;

    /*
     * Obtains: position: Vector3D, direction: Vector3D, material: MaterialObject3D,
     * intensity: double, shadow: boolean Description: Constructor of
     * DirectionalLight Class
     */
    public DirectionalLight(Vector3D position, Vector3D direction, MaterialObject3D material, double intensity,
            boolean shadow) {
        super(position, material, intensity, shadow);
        setDirection(Vector3D.normalize(direction));
    }

    public Vector3D getDirection() {
        return direction;
    }

    public void setDirection(Vector3D direction) {
        this.direction = direction;
    }

    public float getNDotL(Intersection intersection) {
        return (float) Math.max(
                Vector3D.dotProduct(intersection.getNormal(), Vector3D.scalarMultiplication(getDirection(), -1.0)),
                0.0);
    }
}
