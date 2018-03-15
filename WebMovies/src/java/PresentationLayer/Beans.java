package PresentationLayer;

import ApplicationVariables.AppVariables;
import BusinessLayer.MovieBusinessLayer;
import ClassLayer.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;


@ViewScoped
@Named("bean")
public class Beans extends BaseBean implements Serializable{
    
    private MovieBusinessLayer mbl = new MovieBusinessLayer();
    private String selectedFilm, selectedDirector, selectedActor, selectedYear, selectedRating;
    List<Director> directors;
    List<Actor> actors;
    List<SimplisticFilm> sFilms;
    List<String> filmYears, filmRatings;
    private boolean isSubmitted = false, isAllSelected = false;
    
    @PostConstruct
    //Populate drop down menu
    protected void load(){
        if (this.isPostback()){ // Is the page getting generated due to a postback caused by a user's action?
            String filmID = (selectedFilm == null ? null : selectedFilm); //set filmID to selectedFilm
            String directorID = (selectedDirector == null ? null : selectedDirector); //set directorID to selectedDirector
            String actorID = (selectedActor == null ? null : selectedActor); //set actorID to selectedActor
            String filmYear = (selectedYear == null ? null : selectedYear); //set filmYear to selectedYear
            String filmRating = (selectedRating == null ? null : selectedRating); //set filmRating to selectedRating

            populateDropDownsWithFilteredData(filmID, directorID, actorID, filmYear, filmRating); //Populate table based on selected values
        }else{ //Is the page being generated NOT due to postback, eg. for the first time
            populateDropDownsWithOriginalData();
        }
    }
    
    //populate  dropdown lists with ALL data retrieved 
    private void populateDropDownsWithOriginalData(){
        try{
            Films films = mbl.getFilms(); //Get all films through movie business layer
            
            directors = mbl.getDistinctDirectorsFromFilms(films);
            actors = mbl.getDistinctActorsFromFilms(films);
            sFilms = mbl.getDistinctSimplisticFilmsFromFilms(films);
            filmYears = mbl.getDistinctYearsFromFilms(films);
            filmRatings = mbl.getDistinctRatingsFromFilms(films);
        }catch(Exception e){
           e.printStackTrace();
        }
    }
    
    //populate dropdown based on filters currently in place
    private void populateDropDownsWithFilteredData(String filmID, String directorID, String actorID, String filmYear, String filmRating){
        Films films = mbl.getFilms(); 
            
        Films tmp = mbl.getFilmsSubset(filmID, directorID, actorID, filmYear, filmRating, films);

        actors = (actorID == null) ? mbl.getDistinctActorsFromFilms(tmp) : mbl.getDistinctActor(tmp, actorID);
        directors = (directorID == null) ? mbl.getDistinctDirectorsFromFilms(tmp) : mbl.getDistinctDirector(tmp, directorID);
        sFilms = (filmID == null) ? mbl.getDistinctSimplisticFilmsFromFilms(tmp) : tmp.getDistinctSimplisticFilm(filmID);
        filmYears = (filmYear == null) ? mbl.getDistinctYearsFromFilms(tmp) : mbl.getDistinctYear(tmp, filmYear);
        filmRatings = (filmRating == null) ? mbl.getDistinctRatingsFromFilms(tmp) : tmp.getDistinctFilmRating(filmID); 
        
        //display table of data once all values are selected
        if(sFilms.size() >= 1 && directors.size() >= 1 && actors.size() >= 1 && filmYears.size() >= 1){
            isAllSelected = true;
            //this.populateFields(sFilms.get(0).filmID, directors.get(0).personID, actors.get(0).personID);
            String[] filmIDs=new String[sFilms.size()];
            for(int i=0; i<sFilms.size(); i++){
                filmIDs[i]=sFilms.get(i).filmID;
            }
            
            String[] directorIDs=new String[directors.size()];
            for(int i=0; i<directors.size(); i++){
                directorIDs[i]=directors.get(i).personID;
            }
            
            String[] actorIDs=new String[actors.size()];
            for(int i=0; i<actors.size(); i++){
                actorIDs[i]=actors.get(i).personID;
            }
            
            this.populateFields(filmIDs, directorIDs, actorIDs);
        }
    }
    
    //populate and return a list of data based on what the films list currently holds
    public List getFilms(){ return populateDropDownList(this.sFilms);}
    public List getDirectors(){ return populateDropDownList(this.directors); }
    public List getActors(){ return populateDropDownList(this.actors); }
    public List getFilmYears(){ return populateDropDownList(this.filmYears); }
    public List getFilmRatings(){ return populateDropDownList(this.filmRatings); }
    
    
    //http://ruleoftech.com/2012/jsf-1-2-and-getting-selected-value-from-dropdown
    public void filmValueChanged(ValueChangeEvent e){ //Called when user selects a value
        if(isPostback()){
            selectedFilm = e.getNewValue().toString();
            this.load();
        }
    }
    
    public void directorValueChanged(ValueChangeEvent e){
        if(isPostback()){
            selectedDirector = e.getNewValue().toString();
            this.load();
        }
    }
    
    public void actorValueChanged(ValueChangeEvent e){
        if(isPostback()){
            selectedActor = e.getNewValue().toString();
            this.load();
        }
    }
    
    public void yearValueChanged(ValueChangeEvent e){
        if(isPostback()){
            selectedYear = e.getNewValue().toString();
            this.load();
        }
    }
    
    public void ratingValueChanged(ValueChangeEvent e){
        if(isPostback()){
            selectedRating = e.getNewValue().toString();
            this.load();
        }
    }
            
    //refresh the page to initial state
    public void reset() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
    
    
    
    //check if status of page is postback
    public static boolean isPostback() {
       return FacesContext.getCurrentInstance().isPostback();
    }
    
    //+getters, +setters
    public String getSelectedFilm(){return this.selectedFilm;}
    public void setSelectedFilm(String si){this.selectedFilm = si;}
    public String getSelectedDirector(){return this.selectedDirector;}
    public void setSelectedDirector(String si){this.selectedDirector = si;}
    public String getSelectedActor(){return this.selectedActor;}
    public void setSelectedActor(String si){this.selectedActor = si;}
    public String getSelectedYear(){return this.selectedYear;}
    public void setSelectedYear(String si){this.selectedYear = si;}
    public String getSelectedRating(){return this.selectedRating;}
    public void setSelectedRating(String si){this.selectedRating = si;}
    public boolean getIsSubmitted(){return this.isSubmitted;}
    public void setIsSubmitted(Boolean isSubmitted){this.isSubmitted = isSubmitted;}
    public boolean getIsAllSelected(){return this.isAllSelected;}
    public void setIsAllSelected(Boolean isAllSelected){this.isAllSelected = isAllSelected;}
    
    
    //-------------------------------------------------
    //   Populating strings with selected film data
    //-------------------------------------------------
    private Film film;
    private Film[] filmArray;
    
    private Director director;
    private Director[] directorArray;
    
    private Actor actor;
    private Actor[] actorArray;
    
    public void populateFields(String filmID, String directorID, String actorID){
        this.film = mbl.getFilmFromSimplisticFilm(filmID);        
        this.director = mbl.getDirectorFromSimplisticFilm(film, directorID);
        this.actor = mbl.getActorFromSimplisticFilm(film, actorID);
    }
    public void populateFields(String[] filmIDs, String[] directorIDs, String[] actorIDs){
       
        this.filmArray=new Film[filmIDs.length];
        for(int i=0; i<filmIDs.length; i++){
            this.filmArray[i]=mbl.getFilmFromSimplisticFilm(filmIDs[i]);
        }
        
        this.directorArray=new Director[directorIDs.length];
        int dirL = directorIDs.length;
        for(int i=0; i<directorIDs.length; i++){
            Film testFilm = filmArray[0];
            String aId = directorIDs[i];
            int numOfDirsInFilm = filmArray[0].directors.size();
            Director testDirector = filmArray[0].directors.get(0);
            String testDirectorID = filmArray[0].directors.get(0).getID();
            this.directorArray[i]=mbl.getDirectorFromSimplisticFilm(filmArray[0],filmArray[0].directors.get(0).getID());
        }
        
        this.actorArray=new Actor[actorIDs.length];
        for(int i=0; i<actorIDs.length; i++){
            
            this.actorArray[i]=mbl.getActorFromSimplisticFilm(filmArray[0], filmArray[0].actors.get(0).getID());
        }
        
        //this.film = mbl.getFilmFromSimplisticFilm(filmIDs[0]);        
       //this.director = mbl.getDirectorFromSimplisticFilm(film, directorIDs[0]);
        //this.actor = mbl.getActorFromSimplisticFilm(film, actorIDs[0]);
        
        
    }
    
    //JSF read access to fields
    public Film getFilm(){return film;}
    public Film[] getFilmArray(){return filmArray;}
    public Director getDirector(){return director;}
    public Actor getActor(){return actor;}
    public String getFilmImdbLink() {return String.format(AppVariables.WebProperties.imdbFilmURL, film.filmID);}
    public String getDirectorImdbLink() {return String.format(AppVariables.WebProperties.imdbProfileURL, director.personID);}
    public String getActorImdbLink() {return String.format(AppVariables.WebProperties.imdbProfileURL, actor.personID);}
}