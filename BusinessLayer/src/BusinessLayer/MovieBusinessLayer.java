package BusinessLayer;

import ApplicationVariables.AppVariables;
import ClassLayer.*;
import DataLayer.MovieData;
import java.util.List;



public class MovieBusinessLayer {
    String message;
    
     
    public Films getFilms(){
       Films films = new MovieData().getFilmData(AppVariables.CSV.EXTENDED_FILE_PATH); 
        return films;
    }
    
    
    public List<SimplisticFilm> getDistinctSimplisticFilmsFromFilms(Films films){
        return (films == null) ? null : films.toListSimplisticFilm();
    }
    
    public Films getFilmsSubset(String filmID, String directorID, String actorID, String filmYear, String filmRating, Films films){
        return films.getFilmsFilteredSubset(filmID, directorID, actorID, filmYear, filmRating);
    }
    

  public List<Director> getDistinctDirectorsFromFilms(Films films){
       return (films == null) ? null : films.toListDistinctDirector();
    }

    public List<Director> getDistinctDirector(Films films, String directorID){
       return films.getDistinctDirector(directorID);
    }
    
    
    public List<Actor> getDistinctActorsFromFilms(Films films){
        return (films == null) ? null : films.toListDistinctActor();
    }

    public List<Actor> getDistinctActor(Films films, String actorID){
        return films.getDistinctActor(actorID);
    }
    
    
    public List<String> getDistinctYearsFromFilms(Films films){
        return (films == null) ? null : films.toListDistinctYear();
    }
    
    public List<String> getDistinctYear(Films films, String year){
        return films.getDistinctYear(year);
    }
    
    
    public List<String> getDistinctRatingsFromFilms(Films films){
        return (films == null) ? null : films.toListDistinctFilmRatings();
    }
    
    
    public Film getFilmFromSimplisticFilm(String filmID){
        return this.getFilms()
                        .stream()
                        .filter(f -> f.getFilmID().equals(filmID))
                        .findFirst().get();
    }
    
    public Director getDirectorFromSimplisticFilm(Film sFilm, String directorID){
        
        return sFilm.getDirectorList()
                        .stream()
                        .filter(d -> d.getID().equals(directorID))
                        .findFirst().get();
    }
    
    public Actor getActorFromSimplisticFilm(Film sFilm, String actorID){
        return sFilm.getActorList()
                        .stream()
                        .filter(a -> a.getID().equals(actorID))
                        .findFirst().get();
    }
    
        
    public String getMessage(){return message;}
}


