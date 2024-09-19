import java.util.ArrayList;
import java.util.List;

// Singleton para administrar la configuración de autos por usuario
class CarConfigurationManager {
    private static CarConfigurationManager instance;
    private List<Car> cars;

    private CarConfigurationManager() {
        cars = new ArrayList<>();
    }

    public static synchronized CarConfigurationManager getInstance() {
        if (instance == null) {
            instance = new CarConfigurationManager();
        }
        return instance;
    }

    public void configureCar(Car car) {
        cars.add(car);
        System.out.println("Car configured: " + car);
    }

    public List<Car> getConfiguredCars() {
        return cars;
    }

    public void removeCar(int index) {
        if (index >= 0 && index < cars.size()) {
            cars.remove(index);
            System.out.println("Car removed at index " + index);
        } else {
            System.out.println("Invalid index: " + index);
        }
    }
}

// Clase Car para representar el auto y usar el patrón Builder
class Car {
    private String model;
    private String engine;
    private String color;
    private List<String> additionalFeatures;

    private Car(CarBuilder builder) {
        this.model = builder.model;
        this.engine = builder.engine;
        this.color = builder.color;
        this.additionalFeatures = builder.additionalFeatures;
    }

    public String getModel() {
        return model;
    }

    public String getEngine() {
        return engine;
    }

    public String getColor() {
        return color;
    }

    public List<String> getAdditionalFeatures() {
        return additionalFeatures;
    }

    @Override
    public String toString() {
        return "Model: " + model + ", Engine: " + engine + ", Color: " + color +
               ", Additional Features: " + additionalFeatures;
    }

    // Clase interna estática para implementar el patrón Builder
    public static class CarBuilder {
        private String model;
        private String engine;
        private String color;
        private List<String> additionalFeatures = new ArrayList<>();

        public CarBuilder(String model) {
            this.model = model;
        }

        public CarBuilder withEngine(String engine) {
            this.engine = engine;
            return this;
        }

        public CarBuilder withColor(String color) {
            this.color = color;
            return this;
        }

        public CarBuilder addFeature(String feature) {
            this.additionalFeatures.add(feature);
            return this;
        }

        public Car build() {
            return new Car(this);
        }
    }
}

public class main {
    public static void main(String[] args) {
        // Obtenemos la instancia Singleton de CarConfigurationManager
        CarConfigurationManager configManager = CarConfigurationManager.getInstance();

        // Configuramos un auto usando el patrón Builder
        Car car1 = new Car.CarBuilder("SUV")
                            .withEngine("V6")
                            .withColor("Red")
                            .addFeature("Sunroof")
                            .addFeature("Leather Seats")
                            .build();
        configManager.configureCar(car1);

        // Configuramos otro auto
        Car car2 = new Car.CarBuilder("Sedan")
                            .withEngine("V4")
                            .withColor("Blue")
                            .addFeature("GPS")
                            .build();
        configManager.configureCar(car2);

        // Configuramos un tercer auto
        Car car3 = new Car.CarBuilder("Convertible")
                            .withEngine("V8")
                            .withColor("Black")
                            .addFeature("Heated Seats")
                            .build();
        configManager.configureCar(car3);

        // Imprimimos el historial de autos configurados
        List<Car> carHistory = configManager.getConfiguredCars();
        System.out.println("Configured Cars:");
        for (Car car : carHistory) {
            System.out.println(car);
        }

        // Eliminamos el coche en el índice 1
        configManager.removeCar(1);

        // Imprimimos nuevamente el historial de autos configurados después de eliminar
        carHistory = configManager.getConfiguredCars();
        System.out.println("\nUpdated Configured Cars:");
        for (Car car : carHistory) {
            System.out.println(car);
        }
    }
}
