public class FunctionalLinkedList extends LinkedList implements FunctionalList{
    
    public ReturnObject head(){
        if(super.isEmpty())
            return new ReturnObjectImpl(ErrorMessage.EMPTY_STRUCTURE);

        return super.get(0);
    }

    public FunctionalList rest(){
        if(super.isEmpty() || super.size() == 1)
            return new FunctionalLinkedList();

        FunctionalList restOfList = new FunctionalLinkedList();    
        for(int i = 1; i < super.size(); i++){
            restOfList.add(super.get(i).getReturnValue());
        }

        return restOfList;
    }
}