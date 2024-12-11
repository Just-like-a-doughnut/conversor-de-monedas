import com.conversor.calculos.CurrencyConverter;
import com.conversor.modelos.ExchangeRateService;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExchangeRateService exchangeRateService = new ExchangeRateService();
        CurrencyConverter converter = new CurrencyConverter();
        boolean continuar = true;
        while (continuar) {
            try {
                System.out.println("==== Conversor de Monedas ====");
                // Validar monedas
                String baseCurrency;
                do {
                    System.out.print("Moneda base (ej. USD): ");
                    baseCurrency = scanner.nextLine().toUpperCase();
                    if (!exchangeRateService.isValidCurrency(baseCurrency)) {
                        System.out.println("La moneda base no es válida. Por favor, intente de nuevo.");
                    }
                } while (!exchangeRateService.isValidCurrency(baseCurrency));

                String targetCurrency;
                do {
                    System.out.print("Moneda objetivo (ej. EUR): ");
                    targetCurrency = scanner.nextLine().toUpperCase();
                    if (!exchangeRateService.isValidCurrency(targetCurrency)) {
                        System.out.println("La moneda objetivo no es válida. Por favor, intente de nuevo.");
                    }
                } while (!exchangeRateService.isValidCurrency(targetCurrency));

                System.out.print("Cantidad a convertir: ");
                double amount = scanner.nextDouble();
                scanner.nextLine();

                // Realizar la conversión
                double convertedAmount = converter.convert(baseCurrency, targetCurrency, amount);
                System.out.printf("Resultado: %.2f %s\n", convertedAmount, targetCurrency);

                // Preguntar si el usuario desea continuar con otra conversión
                System.out.println("¿Desea realizar otra conversión? (S/N)");
                String response = scanner.nextLine().toUpperCase();

                switch (response) {
                    case "S":
                        System.out.println("Volviendo al menú inicial...");
                        break;
                    case "N":
                        System.out.println("Saliendo del programa...");
                        continuar = false;
                        break;
                    default:
                        System.out.println("Respuesta no válida. Por favor, intente de nuevo.");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}
