package src.tools;

import java.awt.Color;

/**
 *
 * @author Felipe
 */

public class MaterialObject3D extends Material {

	private double ambient;
	private double diffuse;
	private double specular;
	private boolean reflection;
	private boolean refraction;

	/*
	 * Obtains: color: Color, ambient: double, diffuse: double, specular: double,
	 * reflection: boolean, refraction: boolean Description: Constructor of
	 * MaterialObject3D Class
	 */
	public MaterialObject3D(Color color, double ambient, double diffuse, double specular, boolean reflection,
			boolean refraction) {
		super(color);
		setAmbient(ambient);
		setDiffuse(diffuse);
		setSpecular(specular);
		setReflection(reflection);
		setRefraction(refraction);
	}

	public double getAmbient() {
		return ambient;
	}

	public void setAmbient(double ambient) {
		this.ambient = ambient;
	}

	public boolean getReflection() {
		return reflection;
	}

	public void setReflection(boolean reflection) {
		this.reflection = reflection;
	}

	public boolean getRefraction() {
		return refraction;
	}

	public void setRefraction(boolean refraction) {
		this.refraction = refraction;
	}

	public double getDiffuse() {
		return diffuse;
	}

	public void setDiffuse(double diffuse) {
		this.diffuse = diffuse;
	}

	public double getSpecular() {
		return specular;
	}

	public void setSpecular(double specular) {
		this.specular = specular;
	}
}
