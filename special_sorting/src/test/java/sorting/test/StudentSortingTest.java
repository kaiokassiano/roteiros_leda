package sorting.test;
 
import java.util.Arrays;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import sorting.AbstractSorting;
import sorting.variationsOfBubblesort.CombSort;
import sorting.variationsOfBubblesort.GnomeSort;
 
public class StudentSortingTest {
    private Integer[] vetorTamPar;
    private Integer[] vetorTamImpar;
    private Integer[] vetorVazio = {};
    private Integer[] vetorValoresRepetidos;
    private Integer[] vetorValoresIguais;
 
    public AbstractSorting<Integer> implementation;
 
    @Before
    public void setUp() {
        populaVetorTamanhoPar(new Integer[] { 30, 28, 7, 29, 11, 26, 4, 22, 23,
                31 });
        populaVetorTamanhoImpar(new Integer[] { 6, 41, 32, 7, 26, 4, 37, 49,
                11, 18, 36 });
        populaVetorRepetido(new Integer[] { 4, 9, 3, 4, 0, 5, 1, 4 });
        populaVetorIgual(new Integer[] { 6, 6, 6, 6, 6, 6 });
        populaVetor();
 
        getImplementation();
    }
 
    // // MÉTODOS AUXILIARES DA INICIALIZAÇÃO
 
    /**
     * Método que inicializa a implementação a ser testada com a implementação
     * do aluno
     */
    private void getImplementation() {
        // TODO O aluno deve instanciar sua implementação abaixo ao invés de
        // null
        this.implementation = new CombSort<Integer>();
    }
 
    public void populaVetorTamanhoPar(Integer[] arrayPadrao) {
        this.vetorTamPar = Arrays.copyOf(arrayPadrao, arrayPadrao.length);
    }
 
    public void populaVetorTamanhoImpar(Integer[] arrayPadrao) {
        this.vetorTamImpar = Arrays.copyOf(arrayPadrao, arrayPadrao.length);
    }
 
    public void populaVetorRepetido(Integer[] arrayPadrao) {
        this.vetorValoresRepetidos = Arrays.copyOf(arrayPadrao,
                arrayPadrao.length);
    }
 
    public void populaVetorIgual(Integer[] arrayPadrao) {
        this.vetorValoresIguais = Arrays
                .copyOf(arrayPadrao, arrayPadrao.length);
    }
 
    // FIM DOS METODOS AUXILIARES DA INICIALIZAÇÃO
 
    // MÉTODOS DE TESTE
 
    public void genericTest(Integer[] array) {
        Integer[] copy1 = Arrays.copyOf(array, array.length);
        Integer[] copy2 = Arrays.copyOf(array, array.length);
        Integer[] copy3 = Arrays.copyOf(array, array.length);
        implementation.sort(array);
        Arrays.sort(copy1);
        Assert.assertArrayEquals(copy1, array);
 
        if (copy3.length > 0) {
            int i = getRandomNumberInRange(0, array.length / 2);
            implementation.sort(copy2, 0, i);
            Arrays.sort(copy3, 0, i + 1);
        }
        Assert.assertArrayEquals(copy2, copy3);
    }
 
    @Test
    public void testSort01() {
        genericTest(vetorTamPar);
    }
 
    @Test
    public void testSort02() {
        genericTest(vetorTamImpar);
    }
 
    @Test
    public void testSort03() {
        genericTest(vetorVazio);
    }
 
    @Test
    public void testSort04() {
        genericTest(vetorValoresIguais);
    }
 
    @Test
    public void testSort05() {
        genericTest(vetorValoresRepetidos);
    }
 
    // MÉTODOS QUE OS ALUNOS PODEM CRIAR
    /**
     * O ALUNO PODE IMPLEMENTAR METODOS DE ORDENAÇÃO TESTANDO O SORT COM TRES
     * ARGUMENTOS PARA TESTAR A ORDENACAO EM UM PEDAÇO DO ARRAY. DICA: PROCUREM
     * SEGUIR A ESTRUTURA DOS MÉTODOS DE TESTE ACIMA DESCRITOS, ORDENANDO APENAS
     * UMA PARTE DO ARRAY.
     */
    private Integer[] vetor;
 
    private void populaVetor() {
        int size = getRandomNumberInRange(0, 10000);
        vetor = new Integer[size];
        for (int i = 0; i < size; i++) {
            vetor[i] = getRandomNumberInRange(-100000, 100000);
        }
    }
 
    public void genericTest2(Integer[] array) {
    	populaVetor();
    	// Pega dois indices, i ate no maximo a metade, para otimizar testes
        int i = getRandomNumberInRange(0, array.length / 2);
        int j = getRandomNumberInRange(i, array.length);
        // Copiando o array
        Integer[] copy1 = Arrays.copyOf(array, array.length);
        // Ordenando uma parte e comparando
        implementation.sort(array, i, j);
        Arrays.sort(copy1, i, j + 1);
        Assert.assertArrayEquals(copy1, array);
        // Ordenando ele completo (o restante) e comparando
        implementation.sort(array);
        Arrays.sort(copy1);
        Assert.assertArrayEquals(copy1, array);
    }
 
    public void genericTest2(Integer[] array, int i, int j) {
        populaVetor();
        Integer[] copy1 = Arrays.copyOf(array, array.length);
        implementation.sort(array, i, j);
        if (i < 0 || j >= array.length)
            return;    
        Arrays.sort(copy1, i, j + 1);
        Assert.assertArrayEquals(copy1, array);
    }
 
    @Test
    public void testSort10() {
        genericTest2(vetor);
    }
 
    @Test
    public void testSort11() {
        genericTest2(vetor);
    }
 
    @Test
    public void testSort12() {
        genericTest2(vetor);
    }
 
    @Test
    public void testSort13() {
        genericTest2(vetor);
    }
 
    @Test
    public void testSort14() {
        genericTest2(vetor);
    }
 
    @Test
    public void testSort06() {
        genericTest2(vetor);
    }
 
    @Test
    public void testSort07() {
        genericTest2(vetor);
    }
 
    @Test
    public void testSort08() {
        genericTest2(vetor);
    }
 
    @Test
    public void testSort15() {
        genericTest2(vetor, -1, 0);
    }
 
    @Test
    public void testSort16() {
        genericTest2(vetor, 0, 1000000);
    }
 
    @Test
    public void testSort17() {
        genericTest2(vetor, 1, 0);
    }
 
    @Test
    public void testSort18() {
        genericTest2(vetor, -1, 1000000);
    }
   
    @Test
    public void testSortNull() {
        implementation.sort(null, 0, 1);
        implementation.sort(null, -1, 1);
        implementation.sort(null, 1, 0);
        implementation.sort(null, 1, 1000000);
    }
 
    private static int getRandomNumberInRange(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}