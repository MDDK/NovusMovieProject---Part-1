package ClassLayer;
import ApplicationVariables.AppVariables;

public class Director extends Person{
    
    public Director(String directorID, String directorName){
        super (directorID, directorName);
    }
    public String getDirectorImdbLink() {return String.format(AppVariables.WebProperties.imdbProfileURL, personID);}

}
