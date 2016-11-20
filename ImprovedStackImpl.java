public class ImprovedStackImpl implements ImprovedStack{

    private List list;

    public ImprovedStackImpl(List list) {
        this.list = list;
        if(this.list == null)
            this.list = new LinkedList();
    }

    public boolean isEmpty(){
        return this.list.isEmpty();
    }

    public int size(){
        return this.list.size();
    }

    public void push(Object item){
        this.list.add(item);  
    }

    public ReturnObject top(){
        int tailIndex = size() - 1;     
        return this.list.get(tailIndex);
    }

    public ReturnObject pop(){
        int tailIndex = size() - 1;
        return this.list.remove(tailIndex);
    }

    public ImprovedStack reverse(){   
        if(isEmpty())
            return new ImprovedStackImpl(null);          

        return getReversedStack();
    }

    private ImprovedStack getReversedStack(){        
        ImprovedStack reversedStack = new ImprovedStackImpl(new ArrayList());
        int tailIndex = size() - 1;
        for(int i = tailIndex; i >= 0; i--){
            reversedStack.push(this.list.get(i).getReturnValue());
        }
        return reversedStack;
    }

    public void remove(Object object){
        List shortenedList = new ArrayList();

        //while(!isEmpty()) {     
            System.out.println("list size: " + this.list.size());
            System.out.println();

        int iterationLimit = this.list.size();

        for(int i = 0; i < iterationLimit; i++){     
            //System.out.print("list size: "+this.list.size()+" ");
            //writeOrDiscardTopElement(shortenedList, object);
            //System.out.println("This list:\n" + this.list + " shortenedList:\n" + shortenedList);
            //System.out.print(i+" ");
            //System.out.print(pop().getReturnValue()+"|");
            writeOrDiscardTopElement(shortenedList, object);
        }

        System.out.println();
        System.out.println("::");
        System.out.println(shortenedList);

        this.list = shortenedList;

        //System.out.println("Size: " + size());
        //System.out.println(this.list);
    }

    private void writeOrDiscardTopElement(List shortenedList, Object objectToRemove){
        Object topElement = pop().getReturnValue();

        if(topElement == null)  
            return;

        if(topElement.equals(objectToRemove)) 
            return;     

        if(shortenedList.isEmpty()){
            shortenedList.add(topElement);
            return;
        }

        shortenedList.add(0, topElement);
        System.out.print(topElement+"|");
        
    }

    @Override
    public String toString(){
        return this.list.toString();
    }
}