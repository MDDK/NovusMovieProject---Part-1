package ClassLayer;

import java.util.ArrayList;
import java.util.List;

public class Film extends SimplisticFilm {
    public String imdbRating, filmYear;
    public List<Director> directors;
    public List<Actor> actors;
    
    public Film(){
        directors = new ArrayList<Director>();
        actors = new ArrayList<Actor>();
    }
    
    public Film(String filmID, String filmName, String imdbRating, String filmYear){
        super(filmID, filmName);
        this.imdbRating = imdbRating;
        this.filmYear = filmYear;
        directors = new ArrayList<Director>();
        actors = new ArrayList<Actor>();
    }
    
    //The constructor below additionally takes in directors and actors lists
    public Film(String filmID, String filmName, String imdbRating, 
            List<Director> directors, List<Actor> actors, String filmYear){
        super(filmID, filmName);
        this.imdbRating = imdbRating;
        this.directors = directors;
        this.actors = actors;
        this.filmYear = filmYear;
    }
    
    /* not needed because Film is a subclass of SimplisticFilm
    public SimplisticFilm toSimplisticFilm(){
        return (SimplisticFilm)this;
    }*/
    
    
    public String getFilmRating(){return imdbRating;}
    public String getFilmYear(){return filmYear;}
    public List<Director> getDirectorList(){return directors;}

    public List<Actor> getActorList(){return actors;}
    
    
    public void setFilmRatig(String imdbRating){this.imdbRating = imdbRating;}
    public void setFilmYear(String filmYear){this.filmYear = filmYear;}
    public void setDirectorList(List<Director> directors){this.directors = directors;}
    public void setActorList(List<Actor> actors){this.actors = actors;}

}
