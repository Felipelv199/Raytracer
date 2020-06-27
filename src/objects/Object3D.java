/**
 *  2019 - Universidad Panamericana 
 *  All Rights Reserved
 */
package src.objects;

import src.Intersection;
import src.Ray;
import src.Vector3D;
import src.tools.MaterialObject3D;

/**
 *
 * @author Jafet & Felipe
 */
public abstract class Object3D {
    private Vector3D position;
    private MaterialObject3D material;

    /*
     * Obtains: position: Vector3D, material: MaterialObject3D Description:
     * Constructor of Object3D Class
     */
    public Object3D(Vector3D position, MaterialObject3D material) {
        setPosition(position);
        setMaterial(material);
    }

    public Vector3D getPosition() {
        return position;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    public MaterialObject3D getMaterial() {
        return material;
    }

    public void setMaterial(MaterialObject3D material) {
        this.material = material;
    }

    public abstract Intersection getIntersection(Ray ray);

}
