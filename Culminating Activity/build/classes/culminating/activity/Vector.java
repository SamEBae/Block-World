package culminating.activity;

public class Vector {

    private double magnitude;
    private double angle;   // in rads 
//addition of vector class
    public Vector(double magnitude, double angle) {
        this.magnitude = magnitude;
        this.angle = angle;
    }

    public Vector(int x1, int y1, boolean temp) {
        double x = (double)x1;
        double y = (double)y1;        
        magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        if (magnitude != 0) {
            if (x > 0 && y >= 0) {
                angle = Math.atan(y / x);
            } else if (x < 0 && y >= 0) {
                angle = Math.PI - Math.atan(Math.abs(y) / Math.abs(x));
            } else if (x <= 0 && y < 0) {
                angle = Math.PI + Math.atan(Math.abs(y) / Math.abs(x));
            } else if (x >= 0 && y < 0) {
                angle = 2 * Math.PI - Math.atan(Math.abs(y) / Math.abs(x));
            }

        }
    }

    public Vector() {
    }

    public void setMag(double m) {
        magnitude = m;
    }

    public void setAngle(double a) {
        angle = a;
    }

    public double getMag() {
        return magnitude;
    }

    public double getAngle() {
        return angle;
    }

    public void add(Vector b) {
        if (b.magnitude != 0) {
            if (this.magnitude == 0) {
                this.magnitude = b.magnitude;
                this.angle = b.angle;
            } else {
                double x = b.xComponent() + this.xComponent();
                double y = b.yComponent() + this.yComponent();
                magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
                if (x >= 0 && y >= 0) {
                    angle = Math.atan(y / x);
                } else if (x < 0 && y > 0) {
                    angle = Math.PI - Math.atan(Math.abs(y) / Math.abs(x));
                } else if (x < 0 && y < 0) {
                    angle = Math.PI + Math.atan(Math.abs(y) / Math.abs(x));
                } else if (x > 0 && y < 0) {
                    angle = 2 * Math.PI - Math.atan(Math.abs(y) / Math.abs(x));
                }
            }
        }
    }

    public double xComponent() {
        return Math.cos(angle) * magnitude;
    }

    public double yComponent() {
        return Math.sin(angle) * magnitude;
    }

    public void reset() {
        magnitude = 0;
        angle = 0;
    }

    public void printVector() {
        System.out.println("Magnitude: " + magnitude + "   Angle: " + angle);
    }
}
