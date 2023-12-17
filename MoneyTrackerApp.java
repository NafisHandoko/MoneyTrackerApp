import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Singleton Pattern
class MoneyTrackerManager {
    private static MoneyTrackerManager instance;
    private List<Money> moneyList;

    private MoneyTrackerManager() {
        moneyList = new ArrayList<>();
    }

    public static MoneyTrackerManager getInstance() {
        if (instance == null) {
            instance = new MoneyTrackerManager();
        }
        return instance;
    }

    public void addMoney(Money money) {
        moneyList.add(money);
    }

    public List<Money> getMoneyList() {
        return moneyList;
    }
}

// Factory Pattern: (ExpenseFactory & IncomeFactory)
interface MoneyFactory {
    Money createMoney(String description, double amount);
}

class ExpenseFactory implements MoneyFactory {
    @Override
    public Money createMoney(String description, double amount) {
        return new Expense(description, amount);
    }
}

class IncomeFactory implements MoneyFactory {
    @Override
    public Money createMoney(String description, double amount) {
        return new Income(description, amount);
    }
}

// Adapter Pattern
class MoneyAdapter {
    public String formatMoney(Money money) {
        return money.getDescription() + ": Rp " + money.getAmount();
    }
}

// Facade Pattern
class MoneyTrackerFacade {
    private MoneyTrackerManager moneyTrackerManager;
    private MoneyAdapter moneyAdapter;

    public MoneyTrackerFacade() {
        moneyTrackerManager = MoneyTrackerManager.getInstance();
        moneyAdapter = new MoneyAdapter();
    }

    public void addExpense(String description, double amount) {
        MoneyFactory factory = new ExpenseFactory();
        Money expense = factory.createMoney(description, amount);
        moneyTrackerManager.addMoney(expense);
    }

    public void addIncome(String description, double amount) {
        MoneyFactory factory = new IncomeFactory();
        Money income = factory.createMoney(description, amount);
        moneyTrackerManager.addMoney(income);
    }

    public void displayMoneyList() {
        List<Money> moneyList = moneyTrackerManager.getMoneyList();
        for (Money money : moneyList) {
            System.out.println(moneyAdapter.formatMoney(money));
        }
    }
}

// Command Pattern
interface Money {
    String getDescription();

    double getAmount();
}

class Expense implements Money {
    private String description;
    private double amount;

    public Expense(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getAmount() {
        return amount;
    }
}

class Income implements Money {
    private String description;
    private double amount;

    public Income(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getAmount() {
        return amount;
    }
}

public class MoneyTrackerApp {
    public static void main(String[] args) {
        MoneyTrackerFacade moneyTrackerFacade = new MoneyTrackerFacade();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Tambahkan Pengeluaran");
            System.out.println("2. Tambahkan Pemasukan");
            System.out.println("3. Tampilkan Catatan");
            System.out.println("4. Keluar");
            System.out.print("Pilih menu: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Masukkan deskripsi pengeluaran: ");
                    String expenseDescription = scanner.next();
                    System.out.print("Masukkan jumlah pengeluaran: ");
                    double expenseAmount = scanner.nextDouble();
                    moneyTrackerFacade.addExpense(expenseDescription, expenseAmount);
                    break;

                case 2:
                    System.out.print("Masukkan deskripsi pemasukan: ");
                    String incomeDescription = scanner.next();
                    System.out.print("Masukkan jumlah pemasukan: ");
                    double incomeAmount = scanner.nextDouble();
                    moneyTrackerFacade.addIncome(incomeDescription, incomeAmount);
                    break;

                case 3:
                    moneyTrackerFacade.displayMoneyList();
                    break;

                case 4:
                    System.out.println("Keluar dari Money Tracker App!");
                    System.exit(0);

                default:
                    System.out.println("Inputan salah. Masukkan menu yang benar!.");
            }
        }
    }
}
