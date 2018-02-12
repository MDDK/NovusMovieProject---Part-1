package PresentationLayer;

import ApplicationVariables.AppVariables;
import ClassLayer.Person;
import ClassLayer.SimplisticFilm;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.faces.model.SelectItem;


public abstract class BaseBean {
    
   
    protected <T>List populateDropDownList(List<T> dataSource){
        List<SelectItem> siList = new ArrayList();
         
        
        if(dataSource.size() > 1){
            //Add the "No selection option" = <--SELECT--> 
            SelectItem noSelect = new SelectItem(null, AppVariables.WebProperties.dropDownDefault); //(Value, Label)
            noSelect.setNoSelectionOption(true); //Set the value of the noSelectionOption property
            siList.add(noSelect);
        }
        
        
        if(dataSource != null && dataSource.get(0) instanceof SimplisticFilm){ //Are we getting a dataSource of type Simplistic Film?
            List<SimplisticFilm> tmpList = (List<SimplisticFilm>)dataSource;
            siList.addAll(tmpList.stream()
                 .map(f -> new SelectItem(f.getFilmID(), f.getFilmName())) //Map the SimplisticFilm to a SelectedItem
                 .collect(Collectors.toList())); //Accumulate SelectedItems into a list
        }else if(dataSource != null && dataSource.get(0) instanceof Person){ //Are we getting a dataSource of type Person?
            List<Person> tmpList = (List<Person>)dataSource;
            siList.addAll(tmpList.stream()
                 .map(p -> new SelectItem(p.getID(), p.getName())) //Map the Person to a SelectedItem
                 .collect(Collectors.toList()));
        }else if(dataSource != null && dataSource.get(0) instanceof String){ //Are we getting a dataSource of type String?
            List<String> tmpList = (List<String>)dataSource;
            tmpList.stream()
                .map(a -> siList.add(new SelectItem(a))) //Map the String to a SelectedItem
                .collect(Collectors.toList());
        }
        
        return siList; //Returns a list of SelectedItems
    }
    
}
