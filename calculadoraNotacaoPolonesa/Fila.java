class Fila implements Cloneable {

    private int[] vet;
    private int inicio, fim;

    public Fila() {
        this(10);
    }

    public Fila(int qtos) {
        if (qtos < 10)
            qtos = 10;

        vet = new int[qtos];
        inicio = fim = -1;
    }
    
    public Fila(Fila F){
        this(F.vet.length);

        for (int i=inicio; i<=fim ; i++) 
            this.vet[i] =F.vet[i];

        this.inicio = F.inicio;
        this.fim = F.fim;
    }

    public void enfileirar(int E) throws Exception {
        if (filaCheia())
            throw new Exception("Overflow");

        this.fim = (++this.fim)%vet.length;    
        
        vet[this.fim] = E;  
        if (this.inicio == -1)
            this.inicio++;
    }

    public int desenfileirar() throws Exception {
        if (filaVazia())
            throw new Exception("Underflow"); 
            
        int qualElemento = this.inicio;

        if (qtosElementos()==1)
           this.inicio = this.fim = -1;
        else
            this.inicio = ++this.inicio % vet.length;
    
        return vet[qualElemento];  
    }

    public int consulta() throws Exception {
        if (filaVazia())
            throw new Exception("Fila vazia");
        return vet[this.inicio];
    }

    public boolean filaVazia() {
        return (this.inicio == -1);
    }

    public boolean filaCheia() {
        return ((this.fim!=-1) && 
                (qtosElementos() == vet.length));  
    }

    public int qtosElementos() {
        if (filaVazia()) return 0;

        return ((this.inicio<=this.fim) ? 
                (this.fim - this.inicio + 1): // não tem wrap
                (vet.length - (this.inicio - this.fim - 1)));
    }

    public Fila clone(){
        Fila F = new Fila(vet.length);

        for (int i=this.inicio; i<=this.inicio ; i++)
            F.vet[i] = vet[i];
        F.inicio = this.inicio;
        F.fim = this.fim;
        return F;
    }


    public String toString() {
        String retorno = "[";

        if (!filaVazia())
        {
            boolean fezWrap = this.inicio>this.fim;

            if (!fezWrap)
                for (int i = this.inicio; i<=this.fim; i++) {
                    retorno += " " + vet[i];
                }
            else
            {   
                for (int i = this.inicio; i<vet.length; i++) {
                    retorno += " " + vet[i];
                }
                
                for (int i = 0; i<=this.fim; i++) {
                        retorno += " " + vet[i];
                }
            }
        }
        return retorno + " ]";
    }

    public String _toString() {
        if (filaVazia())
            return "0 elementos";

        return "" + vet[this.inicio] + " é o primeiro de " +
                   this.qtosElementos() + " elemento(s)";
    }

}
