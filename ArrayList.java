public class ArrayList implements List{

    private int size = 0;
    private Object[] objectArray;
    //max safe size taken untested as per http://stackoverflow.com/questions/3038392/do-java-arrays-have-a-maximum-size
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    private static final int DEFAULT_STARTING_SIZE = 16;
    private static final int SCALE_FACTOR = 2;

    public ArrayList() {
        this.objectArray = new Object[DEFAULT_STARTING_SIZE];
    }
    
    @Override
	public boolean isEmpty(){
        return this.size <= 0;
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

        if(!object.hasError())
            removeObjectFromIndex(index);            
        
        return object;
    }

    private void removeObjectFromIndex(int index){
        if(isLastIndex(index)){
            removeObjectFromEndOfList();
            return;
        }
        removeObjectFromWithinList(index);
    }

    private void removeObjectFromEndOfList(){
        this.objectArray[this.size-1] = null;
        this.size--;

        if(arrayIsTooLarge())
            reduceArraySize();
    }

    private void removeObjectFromWithinList(int index){
        Object[] newArr = createNewObjectArray();        
        copyArrayTo(this.objectArray, newArr, index);
        copyArrayFromRemoval(this.objectArray, newArr, index);
        this.objectArray = newArr;
        this.size--;
    }

    private boolean isLastIndex(int index){
        return index == this.size - 1;
    }

    private Object[] createNewObjectArray(){
        int newArraySize = this.objectArray.length;

        if(arrayIsTooLarge())
            newArraySize /= SCALE_FACTOR;

        if(arrayIsTooSmall())
            newArraySize *= SCALE_FACTOR;

        return new Object[newArraySize];
    }

    private ReturnObject retrieveObjectFromIndex(int index){
        if(isEmpty())
            return new ReturnObjectImpl(ErrorMessage.EMPTY_STRUCTURE);        

        if(isIndexOutOfBounds(index))
            return new ReturnObjectImpl(ErrorMessage.INDEX_OUT_OF_BOUNDS);

        Object returnObject = this.objectArray[index]; 
        return new ReturnObjectImpl(returnObject);
    }

    private boolean isIndexOutOfBounds(int index){
        return index >= this.size || index < 0;
    }

	
    @Override
	public ReturnObject add(int index, Object item){
        if(item == null)
            return new ReturnObjectImpl(ErrorMessage.INVALID_ARGUMENT);

        if(arrayIsTooSmall() && this.objectArray.length == MAX_ARRAY_SIZE)            
            return new ReturnObjectImpl(ErrorMessage.INDEX_OUT_OF_BOUNDS);

        if(isIndexOutOfBounds(index))
            return new ReturnObjectImpl(ErrorMessage.INDEX_OUT_OF_BOUNDS);
                
        insertArrayElementAt(index, item);   

        return new ReturnObjectImpl(ErrorMessage.NO_ERROR);
    }

    private void insertArrayElementAt(int index, Object item){ 
        Object[] newArr = createNewObjectArray();
        copyArrayTo(this.objectArray, newArr, index);
        newArr[index] = item;
        copyArrayFromInsertion(this.objectArray, newArr, index);
        this.objectArray = newArr;        
        this.size++; 
    }

    
    @Override
	public ReturnObject add(Object item){
        if(item == null)
            return new ReturnObjectImpl(ErrorMessage.INVALID_ARGUMENT);        
        
        if(arrayIsTooSmall() && this.objectArray.length == MAX_ARRAY_SIZE)            
            return new ReturnObjectImpl(ErrorMessage.INDEX_OUT_OF_BOUNDS);

        return appendElementToArrayEnd(item);
    }

    private boolean arrayIsTooSmall(){
        if(this.objectArray.length < this.size + 1)
            return true;
        return false;
    }

    private ReturnObject appendElementToArrayEnd(Object item){

        if(arrayIsTooSmall())
            expandArraySize();

        this.objectArray[this.size] = item;
        this.size++;

        return new ReturnObjectImpl(ErrorMessage.NO_ERROR);
    }

    private boolean arrayIsTooLarge(){
        int sizeCompare = (this.objectArray.length + 1) / SCALE_FACTOR;

        if(sizeCompare > this.size && sizeCompare >= DEFAULT_STARTING_SIZE)
            return true;

        return false;            
    }

    private void  expandArraySize(){
        int newArrSize = this.objectArray.length * SCALE_FACTOR;
        
        if(newArrSize > MAX_ARRAY_SIZE)            
            newArrSize = MAX_ARRAY_SIZE;

        if(newArrSize > this.objectArray.length){
            Object[] newArr = new Object[newArrSize];
            copyArray(this.objectArray, newArr);
            this.objectArray = newArr;
        }        
    }

    private void reduceArraySize(){
        int newArrSize = (this.objectArray.length + 1) / SCALE_FACTOR;

        Object[] newArr = new Object[newArrSize];
        copyArray(this.objectArray, newArr);
        this.objectArray = newArr;
    }
    
    private void copyArray(Object[] source, Object[] destination){
        for(int i = 0; i <= this.size; i++){
            destination[i] = getArrayCopyWriteValue(source, i);
        }
    }

    private void copyArrayTo(Object[] source, Object[] destination, int stopIndex){
        for(int i = 0; i < source.length && i <= stopIndex; i++){
            destination[i] = getArrayCopyWriteValue(source, i);
        }
    }

    private void copyArrayFromRemoval(Object[] source, Object[] destination, int removalIndex){
        for(int i = removalIndex; i < this.size; i++){
            destination[i] = getArrayCopyWriteValue(source, i+1);
        }
    }

    private void copyArrayFromInsertion(Object[] source, Object[] destination, int insertionIndex){
        for(int i = insertionIndex; i < this.size; i++){
            destination[i+1] = getArrayCopyWriteValue(source, i);
        }
    }

    private Object getArrayCopyWriteValue(Object[] source, int index){
        return (index < source.length && index >= 0) ? source[index] : null;
    }

    @Override
    public String toString(){
        String output = ""; //did not use stringbuilder due to constraints of specification
        for(int i = 0; i < this.size; i++){
            output += getStringValue(this.objectArray, i);
        }

        return output;
    }

    private String getStringValue(Object[] arr, int index){
        if(arr[index] != null)
            return arr[index].toString()+"|";
        return "";
    }
}