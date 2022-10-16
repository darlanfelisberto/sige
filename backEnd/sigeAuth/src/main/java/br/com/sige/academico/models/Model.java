package br.com.sige.academico.models;

public abstract class Model {

    protected boolean nova;

    public Model() {
        this.nova = true;
    }

    abstract public Integer getId();

    public int hashCode() {
        return super.hashCode();
    }

    public boolean equals(Model model) {
        if(model == null){
            return false;
        }
        if(!this.getClass().equals(model.getClass())){
            return false;
        }

        return this.getId().equals(model.getId());
    }
}
