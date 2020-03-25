public class Predicate implements Unifiable
{
    private Unifiable[] terms;
    private String predicateName;
    
    public Predicate(String predicateName, Unifiable... args)
    {
        terms = args;
        this.predicateName = predicateName;
    }
    
    private Predicate(Unifiable... args)
    {
        terms = args;
    }
    
    public String toString()
    {
        String s = null;
        for (Unifiable p : terms)
        {
            if (s == null)
            s = p.toString();
            else
            s += " " + p;
        }
        
        if (s == null)
        return "null";

        return predicateName + " (" + s + ")";
    }
    
    public int length()
    {
        return terms.length;
    }
    
    public Unifiable getTerm(int index)
    {
        return terms[index];
    }
    
    public SubstitutionSet unify(Unifiable p, SubstitutionSet s)
    {
        if (p instanceof Predicate)
        {
            Predicate s2 = (Predicate) p;
            if (this.length() != s2.length())
            return null;
            
            SubstitutionSet sNew = new SubstitutionSet(s);
            for (int i = 0; i < this.length(); i++)
            {
                sNew = this.getTerm(i).unify(s2.getTerm(i), sNew);
                if(sNew == null)
                return null;
            }
            return sNew;
        }
        
        if(p instanceof Variable)
        return p.unify(this, s);
        return null;
    }
    
    public PCExpression replaceVariables(SubstitutionSet s)
    {
        Unifiable[] newTerms = new
        Unifiable[terms.length];
        for(int i = 0; i < length(); i++)
        newTerms[i] = (Unifiable)terms[i].replaceVariables(s);
        return new Predicate(newTerms);
    }
}