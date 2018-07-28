package coll;

public class Insertion {

    private long x, a, b, c, out;

    public Insertion(long x, long a, long b, long c, long out) {
        this.x = x;
        this.a = a;
        this.b = b;
        this.c = c;
        this.out = out;
    }

    @Override
    public String toString() {
        return "Insertion{" +
                "out=" + out +
                ", x=" + x +
                ", a=" + a +
                ", b=" + b +
                ", c=" + c +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Insertion insertion = (Insertion) o;

        return out == insertion.out;
    }

    @Override
    public int hashCode() {
        return (int) (out ^ (out >>> 32));
    }
}
