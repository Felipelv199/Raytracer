/**
 *  2019 - Universidad Panamericana
 *  All Rights Reserved
 */
package src;

import src.objects.Sphere;
import src.tools.MaterialObject3D;
import src.tools.OBJReader;
import src.lights.DirectionalLight;
import src.lights.Light;
import src.lights.PointLight;
import src.objects.Camera;
import src.objects.Object3D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.imageio.ImageIO;

/**
 *
 * @author Jafet & Felipe
 */
public class Raytracer {

	/**
	 * Main Function
	 */
	public static void main(String[] args) {
		System.out.println(new Date());
		double ambient = 0.1;// 0.1
		double diffuse = 1.5;// 1.5
		double specular = 0.6;// 0.6
		Scene scene01 = new Scene();

		/*
		 * Scene Objects
		 */
		scene01.setCamera(new Camera(new Vector3D(0.0, 0.0, -10.0), 160, 160, 1200, 1200, 8.2f, 50f));
		scene01.addLight(new PointLight(new Vector3D(5f, 2f, 0f), new Vector3D(0f, 0f, 0f),
				new MaterialObject3D(Color.WHITE, .05, 0.0, 0.0, false, false), 1.1, false));
		scene01.addLight(new PointLight(new Vector3D(-5f, 2f, 0f), new Vector3D(0f, 0f, 0f),
				new MaterialObject3D(Color.WHITE, .05, 0.0, 0.0, false, false), 1.1, false));
		scene01.addObject(new Sphere(new Vector3D(-4f, -2f, 2f), 0.5f,
				new MaterialObject3D(Color.MAGENTA, ambient, diffuse, specular, false, false)));
		scene01.addObject(OBJReader.GetPolygon("objects/cube.obj", new Vector3D(-4f, -3.5f, 2f),
				new MaterialObject3D(Color.GREEN, ambient, diffuse, specular, false, false)));
		scene01.addObject(new Sphere(new Vector3D(-2f, -2f, 1f), 0.5f,
				new MaterialObject3D(Color.RED, ambient, diffuse, specular, false, false)));
		scene01.addObject(OBJReader.GetPolygon("objects/cube.obj", new Vector3D(-2f, -3.5f, 1f),
				new MaterialObject3D(Color.BLUE, ambient, diffuse, specular, false, false)));
		scene01.addObject(new Sphere(new Vector3D(4f, -2f, 2f), 0.5f,
				new MaterialObject3D(Color.MAGENTA, ambient, diffuse, specular, false, false)));
		scene01.addObject(OBJReader.GetPolygon("objects/cube.obj", new Vector3D(4f, -3.5f, 2f),
				new MaterialObject3D(Color.GREEN, ambient, diffuse, specular, false, false)));
		scene01.addObject(new Sphere(new Vector3D(2, -2f, 1f), 0.5f,
				new MaterialObject3D(Color.RED, ambient, diffuse, specular, false, false)));
		scene01.addObject(OBJReader.GetPolygon("objects/cube.obj", new Vector3D(2f, -3.5f, 1f),
				new MaterialObject3D(Color.BLUE, ambient, diffuse, specular, false, false)));
		scene01.addObject(new Sphere(new Vector3D(0f, -2.25f, -.5f), 1.0f,
				new MaterialObject3D(Color.BLACK, ambient, diffuse, specular, false, true)));
		scene01.addObject(OBJReader.GetPolygon("objects/smallTeapot.obj", new Vector3D(0f, -4f, 4f),
				new MaterialObject3D(Color.GREEN, ambient, diffuse, specular, false, false)));
		scene01.addObject(OBJReader.GetPolygon("objects/ring.obj", new Vector3D(0f, 3f, 1f),
				new MaterialObject3D(Color.GREEN, ambient, diffuse, specular, false, false)));
		scene01.addObject(OBJReader.GetPolygon("objects/ground(1).obj", new Vector3D(0f, -4f, 0f),
				new MaterialObject3D(Color.WHITE, ambient, diffuse, specular, false, false)));
		scene01.addObject(OBJReader.GetPolygon("objects/wall.obj", new Vector3D(0f, -4f, 8f),
				new MaterialObject3D(Color.BLACK, ambient, diffuse, specular, true, false)));

		/*
		 * Image Creation
		 */
		BufferedImage image = raytrace(scene01);
		String fileName = new Date().toString().replace(':', '-') + ".png";
		File outputImage = new File("output_images/" + fileName);
		try {
			ImageIO.write(image, "png", outputImage);
		} catch (IOException ex) {
			System.out.println("Something failed");
		}

		System.out.println(new Date());
	}

	/*
	 * Obtains: ray: Ray, objects: ArrayList<Object3D>, caster: Object3D,
	 * clippingPlanes: float[] Description: Obtains an intersection Returns:
	 * raycast: Intersection
	 */
	public static Intersection raycast(Ray ray, ArrayList<Object3D> objects, Object3D caster, float[] clippingPlanes) {
		Intersection closestIntersection = null;

		for (int k = 0; k < objects.size(); k++) {
			Object3D currentObj = objects.get(k);

			if (caster == null || !currentObj.equals(caster)) {
				Intersection intersection = currentObj.getIntersection(ray);
				if (intersection != null) {
					double distance = intersection.getDistance();

					if (distance >= 0 && (closestIntersection == null || distance < closestIntersection.getDistance())
							&& (clippingPlanes == null || (intersection.getPosition().getZ() >= clippingPlanes[0]
									&& intersection.getPosition().getZ() <= clippingPlanes[1]))) {
						closestIntersection = intersection;
					}
				}
			}
		}

		return closestIntersection;
	}

	/*
	 * Obtains: scene: Scene Description: Creates an image Returns: image:
	 * BufferedImage
	 */
	public static BufferedImage raytrace(Scene scene) {
		Camera mainCamera = scene.getCamera();
		ArrayList<Light> lights = scene.getLights();
		float[] nearFarPlanes = mainCamera.getNearFarPlanes();
		BufferedImage image = new BufferedImage(mainCamera.getResolution()[0], mainCamera.getResolution()[1],
				BufferedImage.TYPE_INT_RGB);
		ArrayList<Object3D> objects = scene.getObjects();

		Vector3D[][] positionsToRaytrace = mainCamera.calculatePositionsToRay();
		for (int i = 0; i < positionsToRaytrace.length; i++) {
			for (int j = 0; j < positionsToRaytrace[i].length; j++) {
				double x = positionsToRaytrace[i][j].getX() + mainCamera.getPosition().getX();
				double y = positionsToRaytrace[i][j].getY() + mainCamera.getPosition().getY();
				double z = positionsToRaytrace[i][j].getZ() + mainCamera.getPosition().getZ();
				Ray ray = new Ray(mainCamera.getPosition(), new Vector3D(x, y, z));

				Intersection closestIntersection = raycast(ray, objects, null,
						new float[] { (float) mainCamera.getPosition().getZ() + nearFarPlanes[0],
								(float) mainCamera.getPosition().getZ() + nearFarPlanes[1] });
				// Background color
				Color pixelColor = Color.BLACK;
				if (closestIntersection != null) {
					pixelColor = Color.BLACK;
					for (Light light : lights) {
						Intersection shadowIntersection = raycast(
								new Ray(closestIntersection.getPosition(),
										Vector3D.substract(light.getPosition(), closestIntersection.getPosition())),
								objects, closestIntersection.getObject(),
								new float[] { (float) mainCamera.getPosition().getZ() + nearFarPlanes[0],
										(float) mainCamera.getPosition().getZ() + nearFarPlanes[1] });
						if (shadowIntersection == null && light.getShadow() == true) {
							pixelColor = addColor(pixelColor,
									colorCalculation(closestIntersection, pixelColor, light, mainCamera.getPosition()));

							Vector3D v = Vector3D.substract(closestIntersection.getPosition(),
									mainCamera.getPosition());
							Vector3D n = closestIntersection.getNormal();
							Vector3D r = Vector3D.substract(v,
									Vector3D.scalarMultiplication(n, 2 * Vector3D.dotProduct(v, n)));
							Intersection reflectionIntersection = raycast(new Ray(closestIntersection.getPosition(), r),
									objects, closestIntersection.getObject(),
									new float[] { (float) mainCamera.getPosition().getZ() + nearFarPlanes[0],
											(float) mainCamera.getPosition().getZ() + nearFarPlanes[1] });
							if (closestIntersection.getObject().getMaterial().getReflection() == true
									&& reflectionIntersection != null) {
								pixelColor = colorCalculation(reflectionIntersection, pixelColor, light,
										closestIntersection.getPosition());
							}

							Vector3D d = Vector3D.substract(closestIntersection.getPosition(),
									mainCamera.getPosition());
							double nr = 1.0;
							double nr2 = 1.5;
							Vector3D a = Vector3D.scalarMultiplication(
									Vector3D.substract(d,
											Vector3D.scalarMultiplication(closestIntersection.getNormal(),
													Vector3D.dotProduct(d, closestIntersection.getNormal()))),
									nr / nr2);
							Vector3D b = Vector3D.scalarMultiplication(closestIntersection.getNormal(),
									Math.sqrt(1 - ((Math.pow(nr, 2) * (1
											- Math.pow(Vector3D.dotProduct(d, closestIntersection.getNormal()), 2)))
											/ Math.pow(nr2, 2))));
							Vector3D t = Vector3D.substract(a, b);
							Intersection refractionIntersection = raycast(new Ray(closestIntersection.getPosition(), t),
									objects, closestIntersection.getObject(),
									new float[] { (float) mainCamera.getPosition().getZ() + nearFarPlanes[0],
											(float) mainCamera.getPosition().getZ() + nearFarPlanes[1] });

							if (closestIntersection.getObject().getMaterial().getRefraction() == true
									&& refractionIntersection != null) {
								pixelColor = colorCalculation(refractionIntersection, pixelColor, light,
										closestIntersection.getPosition());
							}
						}

						if (light.getShadow() != true) {
							pixelColor = addColor(pixelColor,
									colorCalculation(closestIntersection, pixelColor, light, mainCamera.getPosition()));

							Vector3D v = Vector3D.substract(closestIntersection.getPosition(),
									mainCamera.getPosition());
							Vector3D n = closestIntersection.getNormal();
							Vector3D r = Vector3D.substract(v,
									Vector3D.scalarMultiplication(n, 2 * Vector3D.dotProduct(v, n)));
							Intersection reflectionIntersection = raycast(new Ray(closestIntersection.getPosition(), r),
									objects, closestIntersection.getObject(),
									new float[] { (float) mainCamera.getPosition().getZ() + nearFarPlanes[0],
											(float) mainCamera.getPosition().getZ() + nearFarPlanes[1] });
							if (closestIntersection.getObject().getMaterial().getReflection() == true
									&& reflectionIntersection != null) {
								pixelColor = colorCalculation(reflectionIntersection, pixelColor, light,
										closestIntersection.getPosition());
							}

							Vector3D d = Vector3D.substract(closestIntersection.getPosition(),
									mainCamera.getPosition());
							double nr = 1.0;
							double nr2 = 1.5;

							Vector3D a = Vector3D.scalarMultiplication(
									Vector3D.substract(d,
											Vector3D.scalarMultiplication(closestIntersection.getNormal(),
													Vector3D.dotProduct(d, closestIntersection.getNormal()))),
									nr / nr2);
							Vector3D b = Vector3D.scalarMultiplication(closestIntersection.getNormal(),
									Math.sqrt(1 - ((Math.pow(nr, 2) * (1
											- Math.pow(Vector3D.dotProduct(d, closestIntersection.getNormal()), 2)))
											/ Math.pow(nr2, 2))));
							Vector3D t = Vector3D.substract(a, b);
							Intersection refractionIntersection = raycast(new Ray(closestIntersection.getPosition(), t),
									objects, closestIntersection.getObject(),
									new float[] { (float) mainCamera.getPosition().getZ() + nearFarPlanes[0],
											(float) mainCamera.getPosition().getZ() + nearFarPlanes[1] });
							if (closestIntersection.getObject().getMaterial().getRefraction() == true
									&& refractionIntersection != null) {
								pixelColor = colorCalculation(refractionIntersection, pixelColor, light,
										closestIntersection.getPosition());
							}
						}
					}
				}
				image.setRGB(i, j, pixelColor.getRGB());
			}
		}

		return image;
	}

	/*
	 * Obtains: value: float, min: float, max: float Description: It's clamp the
	 * value if it pass the max and min Returns: value: float
	 */
	public static float clamp(float value, float min, float max) {
		if (value < min) {
			return min;
		}

		if (value > max) {
			return max;
		}

		return value;
	}

	/*
	 * Obtains: original: Color, anotherColor: Color Description: It makes an
	 * addition of other color to the original color Returns: -:Color
	 */
	public static Color addColor(Color original, Color otherColor) {
		float red = clamp((original.getRed() / 255.0f) + (otherColor.getRed() / 255.0f), 0, 1);
		float green = clamp((original.getGreen() / 255.0f) + (otherColor.getGreen() / 255.0f), 0, 1);
		float blue = clamp((original.getBlue() / 255.0f) + (otherColor.getBlue() / 255.0f), 0, 1);
		return new Color(red, green, blue);
	}

	/*
	 * Obtains: closestIntersection: Intersection, pixelColor: Color, light: Light,
	 * view: Vector3D Description: It generates the pixel color and makes the
	 * calculation off ambientColor, diffuseColor and blinnPhong Returns:
	 * pixelColor: Color
	 */
	public static Color colorCalculation(Intersection closestIntersection, Color pixelColor, Light light,
			Vector3D view) {
		float[] colors = new float[] { closestIntersection.getObject().getMaterial().getColor().getRed() / 255.0f,
				closestIntersection.getObject().getMaterial().getColor().getGreen() / 255.0f,
				closestIntersection.getObject().getMaterial().getColor().getBlue() / 255.0f };

		double ambientColors[] = new double[3];
		if (closestIntersection.getObject().getMaterial().getAmbient() > 0.0) {
			ambientColors[0] = colors[0] * closestIntersection.getObject().getMaterial().getAmbient();
			ambientColors[1] = colors[1] * closestIntersection.getObject().getMaterial().getAmbient();
			ambientColors[2] = colors[2] * closestIntersection.getObject().getMaterial().getAmbient();
			Color ambientColor = new Color(clamp((float) ambientColors[0], 0, 1), clamp((float) ambientColors[1], 0, 1),
					clamp((float) ambientColors[2], 0, 1));
			pixelColor = addColor(pixelColor, ambientColor);
		}
		if (closestIntersection.getObject().getMaterial().getDiffuse() > 0.0) {
			double intensity = light.getIntensity();
			double[] diffuseColors = new double[3];
			if (light instanceof PointLight) {
				diffuseColors[0] = colors[0] * ((PointLight) light).intensityCalculation(closestIntersection)
						* (light.getMaterial().getColor().getRed() / 255.0f)
						* closestIntersection.getObject().getMaterial().getDiffuse();
				diffuseColors[1] = colors[1] * ((PointLight) light).intensityCalculation(closestIntersection)
						* (light.getMaterial().getColor().getGreen() / 255.0f)
						* closestIntersection.getObject().getMaterial().getDiffuse();
				diffuseColors[2] = colors[2] * ((PointLight) light).intensityCalculation(closestIntersection)
						* (light.getMaterial().getColor().getBlue() / 255.0f)
						* closestIntersection.getObject().getMaterial().getDiffuse();
			}
			if (light instanceof DirectionalLight) {
				diffuseColors[0] = colors[0] * intensity * (light.getMaterial().getColor().getRed() / 255.0f)
						* light.getNDotL(closestIntersection)
						* closestIntersection.getObject().getMaterial().getDiffuse();
				diffuseColors[1] = colors[1] * intensity * (light.getMaterial().getColor().getGreen() / 255.0f)
						* light.getNDotL(closestIntersection)
						* closestIntersection.getObject().getMaterial().getDiffuse();
				diffuseColors[2] = colors[2] * intensity * (light.getMaterial().getColor().getBlue() / 255.0f)
						* light.getNDotL(closestIntersection)
						* closestIntersection.getObject().getMaterial().getDiffuse();
			}
			Color diffuseColor = new Color(clamp((float) diffuseColors[0], 0, 1), clamp((float) diffuseColors[1], 0, 1),
					clamp((float) diffuseColors[2], 0, 1));
			pixelColor = addColor(pixelColor, diffuseColor);
		}

		if (closestIntersection.getObject().getMaterial().getSpecular() > 0.0) {
			double[] blinnPhongColor = new double[3];
			Vector3D LV = Vector3D.add(light.getPosition(), view);
			Vector3D H = Vector3D.normalize(LV);
			double NH = Math.pow((Vector3D.dotProduct(closestIntersection.getNormal(), H)), 16);
			blinnPhongColor[0] = colors[0] * NH * closestIntersection.getObject().getMaterial().getSpecular();
			blinnPhongColor[1] = colors[1] * NH * closestIntersection.getObject().getMaterial().getSpecular();
			blinnPhongColor[2] = colors[2] * NH * closestIntersection.getObject().getMaterial().getSpecular();
			Color blingPhong = new Color(clamp((float) blinnPhongColor[0], 0, 1),
					clamp((float) blinnPhongColor[1], 0, 1), clamp((float) blinnPhongColor[2], 0, 1));
			pixelColor = addColor(pixelColor, blingPhong);
		}
		return pixelColor;
	}

}
