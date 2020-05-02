# Java-Obfuscation
Obfuscate java code

Use the code below in your java project to get the names of your variables/methods. Put the names in a text file named "names.txt" and place it in the top level directory of this project. In the same directory put your .java file. Open Main.java and change the javaClassName String so it will point to your .java file. 
 
    public static void getClassVariableAndMethodNames(Class className)
    {
        //Get variables
        Field[] variableNames = className.getDeclaredFields();
        ArrayList<String> namesList = new ArrayList<>();
        Log.e("TAG:" , "getClassVariableAndMethodNames");
        for (Field f : variableNames)
        {
            if (Modifier.isPrivate(f.getModifiers())) {
                namesList.add(f.getName());
            }
        }

        //Get private methods
        Method[] methodNames = className.getDeclaredMethods();
        for (Method m : methodNames)
        {
            if (Modifier.isPrivate(m.getModifiers())) {
                if (namesList.contains(m.getName())) {
                    throw new RuntimeException("WARNING! Variable has same name as method: " + m.getName());
                }
                namesList.add(m.getName());
            }
        }

        String output="";
        int counter=0; 
        for (String s : namesList)
        {
            counter++;
            output = output + "," + s;

            if (counter>100)
            {
                Log.e("Output: ", output);
                output="";
                counter=0;
            }
        }
 
    }
