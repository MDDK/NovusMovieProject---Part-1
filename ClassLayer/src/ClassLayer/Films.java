package ClassLayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Films extends ArrayList<Film>{
    
    public Films() { }

    public Films(List<Film> films){
        this.addAll(films);
    }
    
    //Return only those films which match criteria
    public Films getFilmsFilteredSubset(String filmID, String directorID, String actorID, String filmYear, String filmRating){
        Films tmpFilms = new Films();
        tmpFilms.addAll(this.stream().filter(f -> f.filmID.equals((filmID == null) ? f.filmID : filmID)) //If there is a filmID filter value, allow only films with filmID value equal to it
                                     .filter(f -> f.filmYear.equals((filmYear == null) ? f.filmYear : filmYear)) //As above, filter by filmYear
                                     .filter(f -> f.imdbRating.equals((filmRating == null) ? f.imdbRating : filmRating)) //As above filter by filmRating
                                     .filter(f -> f.directors.stream().anyMatch(p -> p.getID().equals((directorID == null) ? p.getID() : directorID))) //Pass filter if any director ID matches
                                     .filter(f -> f.actors.stream().anyMatch(p -> p.getID().equals((actorID == null) ? p.getID() : actorID))) //Pass filter if any actor ID matches
                                     .sorted(Comparator.comparing(f -> f.getFilmName())) // Use comparator to sort by film name
                                     .collect(Collectors.toList())); //Convert stream to list
        return tmpFilms; //return the filtered and sorted list
    }
    
    
    //Converts self(Films) into a sorted list of SimplisticFilm objects
    public List<SimplisticFilm> toListSimplisticFilm(){
        return this.stream().sorted(Comparator.comparing(fi -> fi.getFilmName()))
                            .collect(Collectors.toList());
        
    }
    
    //Finds a Film with given filmID in self and returns it as a SimplisticFilm
    public List<SimplisticFilm> getDistinctSimplisticFilm(String filmID){
        return this.stream().filter(f -> f.filmID.equals(filmID))
                            .collect(Collectors.toList());
    }
    
    
    public List<Director> toListDistinctDirector(){
        List <Director> tmpList = new ArrayList();
            
        this.stream().flatMap(film -> film.directors.stream()
                    .filter(dir -> tmpList.stream()
                            //Make sure none of the directors we are about to add are duplicates
                            .noneMatch(di -> di.getID().equals(dir.getID()))) 
                     //Add those distinct directors to tmpList
                    .map(nDir -> tmpList.add(nDir)))
                    .collect(Collectors.toList());

        tmpList.sort(Comparator.comparing(c -> c.getName())); //Sort tmpList by name

        return tmpList;   
    }
    
    public List<Director> getDistinctDirector(String directorID){
        List <Director> tmpList = new ArrayList();

        this.stream().flatMap(film -> film.directors.stream()
                    .filter(dir -> tmpList.stream()
                            //Make sure we are not getting duplicates and that we are only getting a director with the specified ID
                            .noneMatch(di -> di.getID().equals(dir.getID())) && dir.getID().equals(directorID))
                    .map(nDir -> tmpList.add(nDir)))
                    .collect(Collectors.toList());

        return tmpList;  
    }
    
    
    public List<Actor> toListDistinctActor(){
        List <Actor> tmpList = new ArrayList();
            
        this.stream().flatMap(film -> film.actors.stream()
                    .filter(act -> tmpList.stream()
                            .noneMatch(ac -> ac.getID().equals(act.getID())))
                    .map(nAct -> tmpList.add(nAct)))
                    .collect(Collectors.toList());

        tmpList.sort(Comparator.comparing(c -> c.getName()));

        return tmpList;   
    }
    
    public List<Actor> getDistinctActor(String actorID){
        List <Actor> tmpList = new ArrayList();

        this.stream().flatMap(film -> film.actors.stream()
                    .filter(act -> tmpList.stream()
                            .noneMatch(ac -> ac.getID().equals(act.getID())) && act.getID().equals(actorID))
                    .map(nAct -> tmpList.add(nAct)))
                    .collect(Collectors.toList());

        return tmpList;  
    }
    
    
    public List<String> toListDistinctYear(){
        List <String> tmpList = new ArrayList();
        
        this.stream()
                .filter(f -> tmpList.stream().noneMatch(y -> y.equals(f.filmYear)))
                .map(f -> tmpList.add(f.filmYear))
                .collect(Collectors.toList());
        
        Collections.sort(tmpList);
                
        return tmpList;  
    }
    
    public List<String> getDistinctYear(String year){
        List <String> tmpList = new ArrayList();
        
        this.stream()
                .filter(f -> tmpList.stream().noneMatch(y -> y.equals(f.filmYear) && f.filmYear.equals(year)))
                .map(f -> tmpList.add(f.filmYear))
                .collect(Collectors.toList());
        
        Collections.sort(tmpList);
                
        return tmpList;   
    }
    
    
    public List<String> toListDistinctFilmRatings(){
        List <String> tmpList = new ArrayList();

         this.stream()
                 .filter(f -> tmpList.stream().noneMatch(y -> y.equals(f.imdbRating)))
                 .map(f -> tmpList.add(f.imdbRating))
                 .collect(Collectors.toList());

         Collections.sort(tmpList);

         return tmpList;
    }
    
    public List<String> getDistinctFilmRating(String imdbRating){
        List <String> tmpList = new ArrayList();
        
        this.stream()
                .filter(f -> tmpList.stream().noneMatch(y -> y.equals(f.imdbRating) && f.imdbRating.equals(imdbRating)))
                .map(f -> tmpList.add(f.imdbRating))
                .collect(Collectors.toList());
        
        Collections.sort(tmpList);
                
        return tmpList;
    }
}
