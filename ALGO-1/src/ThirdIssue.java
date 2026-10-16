import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ThirdIssue {
    Scanner in = new Scanner(System.in);
    HashMap<String, Functions> baseDictionary;
    HashMap<String, Functions> dictionary;
    HashMap<String, Variables> variableDictionary= new HashMap<>();


    public ThirdIssue(HashMap<String, Functions> baseDictionary, HashMap<String, Functions> dictionary) {
        this.baseDictionary = baseDictionary;
        this.dictionary = dictionary;
    }

    public void searchForMissFun(String name){
        if(baseDictionary.containsKey(name)){
            if(dictionary.containsKey(name))
                System.out.println(baseDictionary.get(name)+"is found");
            else System.out.println(baseDictionary.get(name)+"is NOT found");
        }
        else {
            System.out.println(name + " not found in baseDictionary");
        }

    }

    public void missFun(){
        for(String key: baseDictionary.keySet()){
            if(!dictionary.containsKey(key)){
                System.out.println(key+" is NOT found");
            }
        }
    }

    public void addNewFunction(String name){
        System.out.println("Enter the of the function you want to add (Kind,DataType,Parameters,Scope)");
        String line=in.nextLine();
        Functions f=null;
        StringBuilder br = new StringBuilder();
        String[] parts = line.split(",");
        if (parts.length != 4) {
            System.out.println("Error: Invalid input format");
            return;
        }
        String Kind=parts[0];
        String DataType=parts[1];
        String Parameters= parts[2];
        String Scope =parts[3];
        f=new Functions(Kind,DataType,Parameters, Scope);

        baseDictionary.put(name,f);
        System.out.println("Function added successfully");
    }
    public void addNewVar(String name){
        System.out.println("Enter the of the Var you want to add (Kind,DataType,Parameters,line)");
        String line=in.nextLine();
        Variables v=null;

        StringBuilder br = new StringBuilder();
        String[] parts = line.split(",");
        if (parts.length != 4) {
            System.out.println("Error: Invalid input format");
            return;
        }
        String Kind=parts[0];
        String DataType=parts[1];
        String scope= parts[2];
        int Line= Integer.parseInt(parts[3]);
        v=new Variables(Kind,DataType,scope,Line);
        System.out.println("Enter the name of the function you want to add");
        variableDictionary.put(name,v);
        System.out.println("Function added successfully");
    }


    public void use(String use){

        if(baseDictionary.containsKey(use)){
            System.out.println(baseDictionary.get(use)+"is valid");
        }
        else{
            if(variableDictionary.containsKey(use)){
                System.out.println(variableDictionary.get(use)+"is valid");
            }
            else
                System.out.println(use+"is NOT valid");
        }
    }


    public void autoCompleteProperty(String input) {
        boolean found = false;

        for (Map.Entry<String, Functions> entry : dictionary.entrySet()) {
            String name = entry.getKey();
            Functions f = entry.getValue();

            if (name.equals(input) || f.kind.equals(input) || f.dataType.equals(input)
                    || f.parameters.equals(input) || f.scope.equals(input)) {
                System.out.println("Function: " + name);
                System.out.println("Kind: " + f.kind);
                System.out.println("DataType: " + f.dataType);
                System.out.println("Parameters: " + f.parameters);
                System.out.println("Scope: " + f.scope);
                found = true;
            }
        }

        for (Map.Entry<String, Variables> entry : variableDictionary.entrySet()) {
            String name = entry.getKey();
            Variables v = entry.getValue();

            if (name.equals(input) || v.kind.equals(input) || v.type.equals(input)
                    || v.scope.equals(input) || String.valueOf(v.line).equals(input)) {
                System.out.println("Variable: " + name);
                System.out.println("Kind: " + v.kind);
                System.out.println("Type: " + v.type);
                System.out.println("Scope: " + v.scope);
                System.out.println("Line: " + v.line);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Error: Unknown identifier or property");
        }
    }

}
