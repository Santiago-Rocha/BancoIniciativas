package edu.eci.pdsw.managedBeans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.eci.pdsw.entities.Comment;
import edu.eci.pdsw.entities.Initiative;
import edu.eci.pdsw.entities.TypeStatus;
import edu.eci.pdsw.entities.User;
import edu.eci.pdsw.samples.services.InitiativeServices;
import edu.eci.pdsw.samples.services.ServicesException;


@SuppressWarnings("deprecation")
@ManagedBean(name = "dInitiativeBean")
@RequestScoped

public class DescriptionInitiative extends BasePageBean{

	@ManagedProperty(value = "#{param.initiative}")
	private int initiative;
	
	@Inject
	InitiativeServices initiativeService;
	
	private static final long serialVersionUID = 3594009161252782831L;
	
	public void modifyStatus(String newStatus, int id) throws ServicesException{
		initiativeService.modifyInitiative(newStatus, id);
	}
	
	public List<TypeStatus> getTypes () throws ServicesException{
		return initiativeService.listStatus();
	}

	public List<User> getInterested() throws ServicesException{
		return initiativeService.loadInterested(initiative);
	}
	
	public void insertInterested() throws ServicesException{
		initiativeService.insertInterested(initiative, Integer.parseInt(((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("id").toString()));
	}
	
	public void deleteInterested() throws ServicesException{
		initiativeService.deleteInterested(initiative, Integer.parseInt(((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("id").toString()));
	}
	
	public List<Comment> getComments() throws ServicesException{
		return initiativeService.loadComment(initiative);
	}
	
	public void insertComment(String newComment) throws ServicesException{
		initiativeService.addComment(initiative, Integer.parseInt(((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("id").toString()), newComment);
	}
		
	public List<Initiative> getRelated() throws ServicesException{
		return initiativeService.loadRelated(initiative);
	}
	
	public int getInitiative() {
		return initiative;
	}

	public void setInitiative(int initiative) {
		this.initiative = initiative;
	}
	
	public String getDescription() throws ServicesException{
		return initiativeService.searchInitiative(initiative).getDescription();
	}
	
	public String getArea() throws ServicesException{
		return initiativeService.searchInitiative(initiative).getArea();
	}
	
	public String getStatus() throws ServicesException{
		return initiativeService.searchInitiative(initiative).getStatus().getStatus();
	}
	
	public int getVotes() throws ServicesException{
		return initiativeService.searchInitiative(initiative).getNumVotes();
	}
	
	public String getDate() throws ServicesException{
		Locale currentLocale = new Locale ("en", "US") ;
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm", currentLocale);
		String fecha=formatter.format(initiativeService.searchInitiative(initiative).getModifyDate());
		return fecha;
	}
}
