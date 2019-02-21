/*
 * Shipping Store Management Software v0.1
 * Developed for CS3354: Object Oriented Design and Programming.
 * Copyright: Junye Wen (j_w236@txstate.edu)
 */

package shippingstore;

/**
 * Drum is a subclass of Package
 * @author Junye Wen
 */
public class Drum extends Package {

    private String material;
    private float diameter;

    /**
     * Default constructor.
     */
    public Drum() {
        this.material = "";
        this.diameter = 0.0f;
    }

    /**
     * Constructor used to initialize the class fields of the class with the
     * provided values.
     * @param ptn
     * @param specification
     * @param mailingClass
     * @param material
     * @param diameter
     */
    public Drum(String ptn, String specification, String mailingClass, String material, float diameter) {
        super(ptn, specification, mailingClass);
        this.material = material;
        this.diameter = diameter;
    }

    /**
     * Get the drum material.
     * @return material
     */
    public String getMaterial() {
        return material;
    }

    /**
     * set the drum material.
     * @param material
     */
    public void setMaterial(String material) {
        this.material = material;
    }

    /**
     * Get the drum diameter.
     * @return diameter
     */
    public float getDiameter() {
        return diameter;
    }

    /**
     * Set the drum diameter.
     * @param diameter
     */
    public void setDiameter(float diameter) {
        this.diameter = diameter;
    }

    /**
     * Get the attributes of the drum, in a formatted text fashion.
     * @return Formatted text
     */
    @Override
    public String getFormattedText() {
        return String.format("%s %s %s %s Material: %s, Diameter: %.2f %n",
                "Drum", ptn, specification, mailingClass, material, diameter);
    }

    /**
     * Overrides default to string method
     * @return string containing package information
     */
    @Override
    public String toString() {
        return "Envelope{" + "ptn=" + ptn + ", specification=" + specification +
                ", Mailing Class=" + mailingClass + ", height=" + material +
                ", volume=" + diameter + '}';
    }
}
