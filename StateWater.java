public class StateWater 
{    
    int waterArray[];
    
    public StateWater(int[] canArray) { this.canArray = canArray; }
    
    //It has to be a copy of values not reference because we will 
    //create many states and don't want to overwrite the same array.
    public StateWater(StateWater state) {
    	canArray = new int[3];
        for(int i=0; i<3; i++) 
            this.canArray[i] = state.canArray[i];
    }
    
    public boolean equals(Object o)
    {
        StateWater state = (StateWater) o;
        
        for (int i=0; i<3; i++)
            if (this.canArray[i] != state.canArray[i])
                return false;
        
        return true;
    }
    
    public int hashCode() {
        return canArray[0]*100000 + canArray[1]*10000 + canArray[2]*1000 +
               canArray[3]*100 + canArray[4]*10 + canArray[5];
    }    
    
    public String toString()
    {
        String ret = "";
        for (int i=0; i<3; i++)
            ret += " " + this.canArray[i];
        return ret;
    }
}