/**
 *  2019 - Universidad Panamericana 
 *  All Rights Reserved
 */
package src;

/**
 *
 * @author Jafet & Felipe
 */
public class Vector3D {

    private static final Vector3D ZERO = new Vector3D(0.0, 0.0, 0.0);
    private double x, y, z;

    /*
     * Obtains: x: double, y: double, z: double Description: Constructor of Vector3D
     * Class
     */
    public Vector3D(double x, double y, double z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    /*
     * Obtains: vectorA: Vector3D, vectorB: Vector3D Description: It calculates the
     * dotProduct of this two vectors Returns: -: double
     */
    public static double dotProduct(Vector3D vectorA, Vector3D vectorB) {
        return (vectorA.x * vectorB.x) + (vectorA.y * vectorB.y) + (vectorA.z * vectorB.z);
    }

    /*
     * Obtains: vectorA: Vector3D, vectorB: Vector3D Description: It calculates the
     * crossProduct of this two vectors Returns: -: Vector3D
     */
    public static Vector3D crossProduct(Vector3D vectorA, Vector3D vectorB) {
        return new Vector3D((vectorA.y * vectorB.z) - (vectorA.z * vectorB.y),
                (vectorA.z * vectorB.x) - (vectorA.x * vectorB.z), (vectorA.x * vectorB.y) - (vectorA.y * vectorB.x));
    }

    /*
     * Obtains: vectorA: Vector3D Description: It calculates the magnitude of this
     * vector Returns: -: double
     */
    public static double magnitude(Vector3D vectorA) {
        return Math.sqrt(dotProduct(vectorA, vectorA));
    }

    /*
     * Obtains: vectorA: Vector3D, vectorB: Vector3D Description: It calculates the
     * addition of this two vectors Returns: -: Vector3D
     */
    public static Vector3D add(Vector3D vectorA, Vector3D vectorB) {
        return new Vector3D(vectorA.x + vectorB.x, vectorA.y + vectorB.y, vectorA.z + vectorB.z);
    }

    /*
     * Obtains: vectorA: Vector3D, vectorB: Vector3D Description: It calculates the
     * subtraction of this two vectors Returns: -: Vector3D
     */
    public static Vector3D substract(Vector3D vectorA, Vector3D vectorB) {
        return new Vector3D(vectorA.x - vectorB.x, vectorA.y - vectorB.y, vectorA.z - vectorB.z);
    }

    /*
     * Obtains: vector: Vector3D Description: It calculates the vector normalized
     * Returns: -: Vector3D
     */
    public static Vector3D normalize(Vector3D vector) {
        double mag = Vector3D.magnitude(vector);
        return new Vector3D(vector.getX() / mag, vector.getY() / mag, vector.getZ() / mag);
    }

    /*
     * Obtains: vector: Vector3D, scalar: double Description: It calculates the
     * multiplication of the vector with an scalar Returns: -: Vector3D
     */
    public static Vector3D scalarMultiplication(Vector3D vector, double scalar) {
        return new Vector3D(vector.getX() * scalar, vector.getY() * scalar, vector.getZ() * scalar);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    /*
     * Description: It pass the vector to a String Returns: -: String
     */
    public String toString() {
        return "(" + getX() + ", " + getY() + ", " + getZ() + ")";
    }

    /*
     * Description: It copy the vector Returns: -: Vector3D
     */
    public Vector3D clone() {
        return new Vector3D(getX(), getY(), getZ());
    }

    /*
     * Description: It makes a vector(0, 0, 0) Returns: -: String
     */
    public static Vector3D ZERO() {
        return ZERO.clone();
    }
}
