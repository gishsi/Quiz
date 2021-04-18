package with_module;

import java.util.ArrayList;
import java.util.List;

public class Module {
    private String id;
    private List<Bank> banks;

    /**
     *  Constructor for the with_module.Bank class. Needs an ID and to initialize an array list of banks.
     * @param id
     */
    public Module(String id) {
        this.id = id;
        banks = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Bank> getBanks() {
        return banks;
    }

    public void setBanks(List<Bank> banks) {
        this.banks = banks;
    }

    /**
     * Return a bank that matches, could be called search
     */
    public Bank pickBank(Bank targetBank) {
        Bank bank = new Bank();
        for (Bank el: banks) {
            if(!el.equals(targetBank)) {
                bank = null;
                System.out.println("No banks match");
            } else {
                bank = el;
            }
        }
        if(bank==null) return null;
        return bank;
    }

    /**
     * Get each of the banks id
     * @return
     */
    public  List<String> getBankId() {
        List<String> ids = new ArrayList<>();
        for (Bank el: banks) {
            ids.add(el.getId());
        }
        return ids;
    }
    /**
     * Add a new bank
     * @param newBank
     */
    public void addBank(Bank newBank) {
        if(newBank!=null) {
            banks.add(newBank);
        }
    }

    @Override
    public String toString() {
        return "with_module.Module{" +
                "\n\tid='" + id + '\'' +
                ", \n\tbanks=" + banks +
                "\n}";
    }


}
