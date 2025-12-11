import dao.*;
import model.*;
import java.util.*;
import java.math.BigDecimal;

public class App {
    private static final ProducteDAO producteDAO = new ProducteDAO();
    private static final ComandaTransaccioDAO comandaDAO = new ComandaTransaccioDAO();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int op;
        do {
            System.out.println("1. Llistar productes");
            System.out.println("2. Crear comanda (transacció B)");
            System.out.println("0. Sortir");
            op = Integer.parseInt(sc.nextLine());
            try {
                switch (op) {
                    case 1 -> producteDAO.llistar().forEach(System.out::println);
                    case 2 -> {
                        System.out.print("Client id: ");
                        int cid = Integer.parseInt(sc.nextLine());
                        System.out.print("Quantes línies?: ");
                        int n = Integer.parseInt(sc.nextLine());
                        List<LiniaComanda> linies = new ArrayList<>();
                        for (int i = 0; i < n; i++) {
                            System.out.print("Producte id: ");
                            int pid = Integer.parseInt(sc.nextLine());
                            Producte p = producteDAO.buscarPerId(pid);
                            if (p == null) {
                                System.out.println("No trobat");
                                i--;
                                continue;
                            }
                            System.out.print("Quantitat: ");
                            int q = Integer.parseInt(sc.nextLine());
                            linies.add(new LiniaComanda(pid, q, p.getPreu()));
                        }
                        System.out.print("Forçar error notificació (s/n)?: ");
                        boolean err = sc.nextLine().equalsIgnoreCase("s");
                        int idc = comandaDAO.crearComandaSimple(cid, linies, err);
                        System.out.println("Comanda creada id=" + idc);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (op != 0);
        sc.close();
    }
}
