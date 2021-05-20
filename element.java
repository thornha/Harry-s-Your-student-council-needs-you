
/**
 * Write a description of class element here.
 *
 * @author harry thornburrow
 * @version 20/05/2021
 */
public class element<E>
{
    // instance variables - replace the example below with your own
    public E name;
    public element follower;
    public element()//makes new element with default name
    {
        // initialise instance variables
        
    }
    public element(E name)//makes new element with name
    {
        this.name=name;
    }
    public void setName(E name)//changes name to name
    {
        this.name=name;
    }
    public E getName(){
        return this.name;
    }
    public void createfollower()//creates new element being follower
    {
        this.follower=new element();
    }
    public void createfollower(E name)//creates new element with name
    {
        this.follower=new element(name);
    }
    public void addfollower(element follower)//adds a new follower
    {
        this.follower=follower;
    }
    public element nextfollower(){//returns the element following this one
        return this.follower;
    }
}
