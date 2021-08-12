
/**
 * this is the element which is what is put throught the queue
 *
 * @author harry thornburrow
 * @version 20/05/2021
 */
public class element<E>
{
    public E name;
    public element follower;
        public element()//makes new element with default name
    {
        
        
    }
    public element(E name)//makes new element with name
    {
        this.name=name;
    }
    public void setName(E name)//changes name to name
    {
        this.name=name;
    }
    public E getName(){//gets the name
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
