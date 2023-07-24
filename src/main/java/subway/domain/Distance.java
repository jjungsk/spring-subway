package subway.domain;

import java.util.Objects;

public class Distance {

    private final int distance;

    public Distance(int distance) {
        validateDistance(distance);
        this.distance = distance;
    }

    private void validateDistance(int distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("거리는 0 이하일 수 없습니다.");
        }
    }

    public int compareDistance(Distance otherDistance) {
        return Integer.compare(this.distance, otherDistance.distance);
    }

    public int subtract(Distance otherDistance) {
        return this.distance - otherDistance.distance;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Distance distance1 = (Distance) o;
        return distance == distance1.distance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(distance);
    }

    @Override
    public String toString() {
        return "Distance{" +
            "distance=" + distance +
            '}';
    }
}
