package ABC_Enterprise;

public class Manufacturer {
    Address addr;
    String comp_name;

    Manufacturer(Address adr, String cmp_name){
        addr = adr;
        comp_name = cmp_name;
    }
    public Address getAddress(){
        return addr;
    }
    public String getCompanyName(){
        return comp_name;
    }
}
