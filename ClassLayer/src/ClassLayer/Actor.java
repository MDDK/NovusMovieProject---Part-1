package ClassLayer;
import ApplicationVariables.AppVariables;

public class Actor extends Person{
    
    public Actor(String actorID, String actorName){
        super (actorID, actorName);
    }
    
    public String getActorImdbLink() {return String.format(AppVariables.WebProperties.imdbProfileURL, personID);}

    
}


