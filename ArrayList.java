public class ArrayList implements List{

    private int size = 0;
    private Object[] arr;
    private final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    public ArrayList(){
        this(5);
    }

    public ArrayList(int startingSize){
        this.arr = new Object[startingSize];
    }

    public int getArraySize(){
        return this.arr.length;
    }
    
    @Override
	public boolean isEmpty(){
        return this.size == 0;
    }

    @Override
	public int size(){
        return this.size;
    }

    @Override
	public ReturnObject get(int index){
        return retrieveObjectFromIndex(index);      
    }

	
    @Override
	public ReturnObject remove(int index){        
        ReturnObject object = retrieveObjectFromIndex(index);

        if(!object.hasError()){
            removeObjectFromIndex(index);
            this.size--;
        }

        verifyArrayOvercapacity();

        return object;
    }

    private void removeObjectFromIndex(int index){
        Object[] newArr = new Object[this.arr.length];
        copyArrayTo(this.arr, newArr, index);
        copyArrayFromRemoval(this.arr, newArr, index);
        this.arr = newArr;
    }

    private ReturnObject retrieveObjectFromIndex(int index){
        ErrorMessage error = ErrorMessage.NO_ERROR;
        Object returnObject = null;

        if(this.size == 0){
            error = ErrorMessage.EMPTY_STRUCTURE;
        } 
        else if(index >= this.size || index < 0){
            error = ErrorMessage.INDEX_OUT_OF_BOUNDS;
        }
        else{
            returnObject = this.arr[index];
        }

        return new ReturnObjectImpl(returnObject, error);
    }

	
    @Override
	public ReturnObject add(int index, Object item){

        if(item == null)
            return new ReturnObjectImpl(null, ErrorMessage.INVALID_ARGUMENT);

        if(index >= this.size || index < 0)
            return new ReturnObjectImpl(null, ErrorMessage.INDEX_OUT_OF_BOUNDS);
        
        verifyArraySize();
        insertArrayElementAt(index, item);
        this.size++;    

        return new ReturnObjectImpl(null, null);
    }

    private void insertArrayElementAt(int index, Object item){        
        Object[] newArr = new Object[this.arr.length];
        copyArrayTo(this.arr, newArr, index);
        newArr[index] = item;
        copyArrayFromInsertion(this.arr, newArr, index);
        this.arr = newArr;
    }

    
    @Override
	public ReturnObject add(Object item){

        if(item == null)
            return new ReturnObjectImpl(null, ErrorMessage.INVALID_ARGUMENT);


        verifyArraySize();
        this.arr[this.size] = item;
        this.size++;

        return new ReturnObjectImpl(null, null);
    }

    private void verifyArraySize(){
        if(this.arr.length < this.size + 1)
            expandArraySize();        
    }

    private void verifyArrayOvercapacity(){
        if((this.arr.length + 1) / 2 > this.size)
            reduceArraySize();
    }

    private void expandArraySize(){
        int newArrSize = this.arr.length * 2;
        if(newArrSize > MAX_ARRAY_SIZE)
            newArrSize = MAX_ARRAY_SIZE;

        if(newArrSize > this.arr.length){
            Object[] newArr = new Object[newArrSize];
            copyArray(this.arr, newArr);
            this.arr = newArr;
        }
    }

    private void reduceArraySize(){
        int newArrSize = (this.arr.length + 1) / 2;

        Object[] newArr = new Object[newArrSize];
        copyArray(this.arr, newArr);
        this.arr = newArr;
    }
    
    private void copyArray(Object[] source, Object[] destination){
        for(int i = 0; i < this.size; i++){
            destination[i] = getArrayCopyWriteValue(source, i);
        }
    }

    private void copyArrayTo(Object[] source, Object[] destination, int stopIndex){
        for(int i = 0; i < source.length && i <= stopIndex; i++){
            destination[i] = getArrayCopyWriteValue(source, i);
        }
    }

    private void copyArrayFromRemoval(Object[] source, Object[] destination, int removalIndex){
        for(int i = removalIndex; i < source.length; i++){
            destination[i] = getArrayCopyWriteValue(source, i+1);
        }
    }

    private void copyArrayFromInsertion(Object[] source, Object[] destination, int insertionIndex){
        for(int i = insertionIndex+1; i < source.length; i++){
            destination[i] = getArrayCopyWriteValue(source, i-1);
        }
    }

    private Object getArrayCopyWriteValue(Object[] source, int index){
        return (index < source.length && index >= 0) ? source[index] : null;
    }

    @Override
    public String toString(){
        String output = ""; //did not use stringbuilder due to constraints of specification
        for(int i = 0; i < this.size; i++){
            output += getStringValue(this.arr, i);
        }

        return output;
    }

    private String getStringValue(Object[] arr, int index){
        if(arr[index] != null)
            return arr[index].toString()+"\n";

        return "";
    }

	
}