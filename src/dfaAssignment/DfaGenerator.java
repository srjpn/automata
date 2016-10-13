package dfaAssignment;
import dfaAssignment.tuple.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Set;

public class DfaGenerator {

    public DFA fromJson(JSONObject tuple) throws InvalidTupleException {
        States states = parseStates((JSONArray) tuple.get("states"));

        Alphabets alphabets = new Alphabets();
        for (Object alphabet : (JSONArray) tuple.get("alphabets")) {
            alphabets.add(new Alphabet((String) alphabet));
        }
        State initialState = new State((String) tuple.get("start-state"));
        States finalStates = parseStates((JSONArray) tuple.get("final-states"));
        Transsitions transsitions = parseTranssitions((JSONObject) tuple.get("delta"));

        return DFA.generateDFA(states, alphabets, transsitions, initialState, finalStates);
    }

    private States parseStates(JSONArray jsonArray){
        States states = new States();
        for (Object state : jsonArray) {
            states.add(new State((String) state));
        }
        return states;
    }

    private Transsitions parseTranssitions(JSONObject jsonObject){
        Transsitions transsitions = new Transsitions();
        Set set = jsonObject.keySet();
        for (Object from : set) {
            JSONObject o = (JSONObject) jsonObject.get(from);
            Set innerKeySet = o.keySet();
            for (Object alphabet : innerKeySet) {
                transsitions.add(new Transsition(new State((String) from), new Alphabet((String) alphabet), new State((String) o.get(alphabet))));
            }
        }
        return transsitions;
    }

    public ArrayList<Alphabets> parseCases(JSONArray jsonObject){
        ArrayList<Alphabets> alphabetses = new ArrayList<Alphabets>();
        for (Object string : jsonObject) {
            Alphabets alphabets = new Alphabets();
            String[] string1 = ((String) string).split("");
            for (String alphabet : string1) {
                alphabets.add(new Alphabet(alphabet));
            }
            alphabetses.add(alphabets);
        }
        return alphabetses;
    }
}
