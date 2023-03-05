import java.util.Locale;
import java.util.Scanner;

public class Main {
    // tudo de escopo global precisam ser orientado a objetos ou ter o parametro static
    //variaveis globais
    public static Scanner input = new Scanner(System.in);
    public static Scanner input_texto = new Scanner(System.in);
    public static int num_clientes;
    public static int num_telemoveis;
    public static boolean desligar = false;

    public static String[][] clientes = new String[999][4];
    //col 0 = nome
    //col 1 = NIF
    //col 2 = Morada
    //col 3 = qtd de telemóveis
    public static String[][] telemoveis = new String[999][3];
    //col 0 = numero
    //col 1 = modelo
    //col 2 = NIF do dono (cliente)

    public static void main(String[] args) {
        //variaveis locais
        input.useLocale(Locale.US);

        clientes[0][0] = "Jorge";clientes[0][1] = "111";clientes[0][2] = "Bocage";clientes[0][3] = "1";
        clientes[1][0] = "Matheus";clientes[1][1] = "222";clientes[1][2] = "Bocage";clientes[1][3] = "1";
        clientes[2][0] = "Alexandre";clientes[2][1] = "333";clientes[2][2] = "Aveiro";clientes[2][3] = "1";
        clientes[3][0] = "Rodolfo";clientes[3][1] = "444";clientes[3][2] = "Aveiro";clientes[3][3] = "1";
        clientes[4][0] = "Pedro";clientes[4][1] = "555";clientes[4][2] = "Santo Tirso";clientes[4][3] = "1";
        clientes[5][0] = "Relber";clientes[5][1] = "666";clientes[5][2] = "Braga";clientes[5][3] = "1";
        clientes[6][0] = "Ana Frias";clientes[6][1] = "777";clientes[6][2] = "Braga";clientes[6][3] = "1";
        num_clientes = 7;

        telemoveis[0][0] = "100";telemoveis[0][1] = "S20";telemoveis[0][2] = "111";
        telemoveis[1][0] = "200";telemoveis[1][1] = "S21+";telemoveis[1][2] = "222";
        telemoveis[2][0] = "300";telemoveis[2][1] = "Iphone 13";telemoveis[2][2] = "333";
        telemoveis[3][0] = "400";telemoveis[3][1] = "Iphone 11";telemoveis[3][2] = "444";
        telemoveis[4][0] = "500";telemoveis[4][1] = "Motolora";telemoveis[4][2] = "555";
        telemoveis[5][0] = "600";telemoveis[5][1] = "S22";telemoveis[5][2] = "666";
        telemoveis[6][0] = "700";telemoveis[6][1] = "Iphone 13 SE";telemoveis[6][2] = "777";
        num_telemoveis = 7;

        int option;
        do {
            limpa();
            menu();
            option = input.nextInt();
            System.out.println("\n\n");

            switch (option) {
                case 1: registarCliente();break;
                case 2: editarCliente();break;
                case 3: apagarCliente();break;
                case 4: registarTelemovel();break;
                case 5: editarTelemovel();break;
                case 6: apagarTelemovel();break;
                case 7: listarClientes();break;
                case 8: listarTelemoveis();break;
                case 9: buscarCliente(); break;
                case 10:
                    limpa();
                    sair();
                    break;
                default:
                    System.out.println("Opcão inválida!");
                    tecleEnter();
                    break;
            }
        } while (!desligar);
    }

    public static void menu() {
        System.out.println("===== Vodafone da vida=====\n");
        System.out.println("1 - Registrar cliente.");
        System.out.println("2 - Editar cliente.");
        System.out.println("3 - Apagar cliente.");

        System.out.println("\n4 - Registrar telemóvel.");
        System.out.println("5 - Editar telemóvel.");
        System.out.println("6 - Apagar telemóvel.");

        System.out.println("\n7 - Listar clientes");
        System.out.println("8 - Listar telemóveis");
        System.out.println("9 - Buscar cliente específico");

        System.out.println("\n10 - Logoff");
        System.out.print("\nOption: ");
    }
    public static void registarCliente(){ //pronta
        String buffer_1;
        boolean found = false;

        System.out.println("----- Registrar Cliente -----\n");
        System.out.print("NIF a ser registrado: ");
        buffer_1 = input_texto.nextLine();
        for (int i = 0; i < num_clientes; i++){
            if (buffer_1.equalsIgnoreCase(clientes[i][1])){
                switchErros(2); // NIF já resgistrado
                found = true;
            }
        }
        if (!found){
            clientes[num_clientes][1] = buffer_1;

            System.out.print("\nDigite o nome do cliente: ");
            clientes[num_clientes][0] = input_texto.nextLine();

            System.out.print("\nMorada do cliente: ");
            clientes[num_clientes][2] = input_texto.nextLine();

            clientes[num_clientes][3] = "0";

            num_clientes++;

            switchErros(1); // sucesso
        }
    }
    public static void editarCliente(){ //pronta
        boolean found = false;
        int client_id = -99;
        System.out.print("Qual o NIF do cliente que desejas editar: ");
        String nif = input_texto.nextLine();
        for (int i = 0; i < num_clientes; i++){
            if (clientes[i][1].equalsIgnoreCase(nif)){
                client_id = i;
                found = true;
                break;
            }
        }
        if (found){
            int opcao = -99;
            do {
                menuEdicaoCliente(client_id);
                opcao = input.nextInt();

                switch (opcao){
                    case 1: editarNome(client_id); break;
                    case 2: editarNif(nif,client_id); break;
                    case 3: editarMorada(client_id); break;
                    case 0: limpa(); break;
                    default: System.out.println("Opcão inválida!"); break;
                }
            }while(opcao != 0);

        }else {
            switchErros(3); // NIF nao resgistrado
        }
    }

    public static void menuEdicaoCliente(int client_id){
        limpa();
        listarUmCliente(client_id);
        System.out.println();
        System.out.println("----- Edição de clientes da Vodafone da vida -----\n");
        System.out.println("1 - Editar Nome.");
        System.out.println("2 - Editar NIF.");
        System.out.println("3 - Editar Morada.");
        System.out.println("\n0 - Voltar ao menu anterior.");
        System.out.print("Opção: ");
    }
    public static void editarNome(int client_id){
        limpa();
        System.out.print("Novo nome: ");
        String novo_nome = input_texto.nextLine();
        clientes[client_id][0] = novo_nome;
        System.out.println("Nome do cliente editado com sucesso!\n");
    }

    public static void editarNif(String nif, int client_id){
        limpa();
        boolean trocarNif = false;
        System.out.print("Novo NIF: ");
        String novo_nif = input_texto.nextLine();

        if (!novo_nif.equalsIgnoreCase(nif)){
            if (verificarNif(novo_nif) >= 0){
                switchErros(2); // NIF já resgistrado
            }else{
                trocarNif = true;
            }
        }

        if (trocarNif){
            clientes[client_id][1] = novo_nif;
            System.out.println("NIF do cliente editado com sucesso!\n");

            for (int l = 0; l < num_telemoveis; l++){
                if (nif.equalsIgnoreCase(telemoveis[l][2])){
                    System.out.println("Telemovel (localização " +l+") - marca ["+telemoveis[l][1]+"], nº"+telemoveis[l][0]+", editado para conter o NIF {"+novo_nif+"}.");
                    telemoveis[l][2] = novo_nif;
                }
            }
            System.out.println();
            tecleEnter();
        }
    }

    public static void editarMorada(int client_id){
        limpa();
        System.out.print("Nova morada: ");
        String nova_morada = input_texto.nextLine();
        clientes[client_id][2] = nova_morada;
        System.out.println("Morada do cliente editada com sucesso!\n");
    }
    public static void apagarCliente() { //pronta
        int client_id = -99;
        boolean found_nif = false;

        System.out.print("NIF do cliente que desejas apagar do cadastro: ");
        String nif = input_texto.nextLine();

        for (int i = 0; i < num_clientes; i++) {
            if (clientes[i][1].equalsIgnoreCase(nif)) {
                client_id = i;
                found_nif = true;
                break;
            }
        }
        if (found_nif){
            listarUmCliente(client_id);
            System.out.println();
            System.out.print("Desejas apagar o cliente ("+clientes[client_id][0]+") da base de dados (sim/nao): ");
            String resposta = input_texto.nextLine();
            if (resposta.equalsIgnoreCase("sim")) {
                //Apagando cliente
                        //essa sessao serve so pra previnir bug no matrix[0][]
                clientes[num_clientes][0] = ""; //nome
                clientes[num_clientes][1] = ""; //nif
                clientes[num_clientes][2] = ""; //morada
                clientes[num_clientes][3] = ""; //qtd de telemoveis

                for (int j = client_id; j < num_clientes; j++){
                    clientes[j][0] = clientes[j+1][0];
                    clientes[j][1] = clientes[j+1][1];
                    clientes[j][2] = clientes[j+1][2];
                    clientes[j][3] = clientes[j+1][3];
                }
                deletarTelemoveis(nif);
                num_clientes--;
                switchErros(1); //sucesso
            }else {
                System.out.println("Operação cancelada");
                tecleEnter();
            }
        }else {
            switchErros(3); //nif nao encontrado
        }
    }

    public static void registarTelemovel() { // pronta
        boolean found_nif = false, same_number = false, finalizado = false;
        int cliente_id = -99;

        if (num_telemoveis == 0) {
            registroSimplesTelemovel();
        } else {
            System.out.print("NIF do dono do telemóvel: ");
            String nif = input_texto.nextLine();
            for (int i = 0; i < num_clientes; i++) {
                if (clientes[i][1].equalsIgnoreCase(nif)) {
                    found_nif = true;
                    cliente_id = i;
                }
            }
            String numero_tel = null;
            if (found_nif) {
                System.out.print("Nº do telemóvel: ");
                numero_tel = input_texto.nextLine();
                for (int j = 0; j < num_telemoveis; j++) {
                    if (telemoveis[j][0].equalsIgnoreCase(numero_tel)) {
                        switchErros(4); // erro Numero de Telemovel já Existe
                        same_number = true;
                    }
                }
            }
            else {
               switchErros(3); //erro, nif não existe existe
            }
            if (found_nif && !same_number) {
                System.out.print("Modelo do telemóvel: ");
                telemoveis[num_telemoveis][1] = input_texto.nextLine(); //recebe valor do modelo modelo
                telemoveis[num_telemoveis][0] = numero_tel;
                telemoveis[num_telemoveis][2] = nif;

                //aumentando o numero de telemoveis do cliente
                int buffer = Integer.parseInt(clientes[cliente_id][3]) + 1;
                clientes[cliente_id][3] = String.valueOf(buffer);
                num_telemoveis++;

                switchErros(1); //sucesso
            }
        }
    }

    public static void registroSimplesTelemovel() { // pronta

        System.out.print("Nº do telemóvel: ");
        telemoveis[0][0] = input_texto.nextLine();
        System.out.print("Modelo do telemóvel: ");
        telemoveis[0][1] = input_texto.nextLine();
        System.out.print("NIF do dono do telemóvel: ");
        telemoveis[0][2] = input_texto.nextLine();

        num_telemoveis++;
        //aumentando o numero de telemoveis do cliente
        int buffer = Integer.parseInt(clientes[0][3]) + 1;
        clientes[0][3] = String.valueOf(buffer);

        switchErros(1); //sucesso
    }
    public static void editarTelemovel(){ //pronta
        listarTelemoveisSimples();
        int tel_ID = -99999;
        boolean found_number = false;

        System.out.print("Qual o Nº do telemóvel que desejas editar: ");
        String numero_tel = input_texto.nextLine();
        for (int i = 0; i < num_telemoveis; i++){
            if (telemoveis[i][0].equalsIgnoreCase(numero_tel)){
                tel_ID = i;
                found_number = true;
                break;
            }
        }
        if (found_number) {
            int opcao = -99;
            do {
                menuEdicaoTel(tel_ID);
                opcao = input.nextInt();

                switch (opcao){
                    case 1: editarNumero(tel_ID); break;
                    case 2: editarModelo(tel_ID); break;
                    case 3: editarNifTel(tel_ID); break;
                    case 0: limpa(); break;
                    default: System.out.println("Opcão inválida!"); break;
                }
            }while (opcao != 0);

        }else{
            switchErros(5); // numero tel n encontrado
        }
    }

    public static void menuEdicaoTel(int Tel_ID){
        limpa();

        int cliente_id = -99;
        //achando o nif do dono a partir do nº de tel
        String nif = telemoveis[Tel_ID][2];
        for (int i = 0; i < num_clientes; i++){
            if (nif.equalsIgnoreCase(clientes[i][1])){
                cliente_id = i;
                break;
            }
        }
        listarUmTelemovel(Tel_ID);
        System.out.println("\n----- Edição de telemoveis da Vodafone da vida -----\n");
        System.out.println("1 - Editar Nº de telemovel.");
        System.out.println("2 - Editar modelo do telemovel.");
        System.out.println("3 - Editar NIF do dono do telemovel.");
        System.out.println("\n0 - Voltar ao menu anterior.");
        System.out.print("Opção: ");
    }

    public static void editarNumero(int tel_ID){
        limpa();
        boolean numero_ok = true;

        System.out.print("Novo nº do telemóvel: ");
        String novo_numero = input_texto.nextLine();

        for (int i = 0; i < num_telemoveis; i++){
            if (novo_numero.equalsIgnoreCase(telemoveis[i][0])){
                switchErros(4); // numero já resgitado
                numero_ok = false;
                break;
            }
        }
        if (numero_ok){
            telemoveis[tel_ID][0] = novo_numero;
            System.out.println("Nº do telemovel editado com sucesso!\n");
            tecleEnter();
        }
    }
    public static void editarModelo(int tel_ID){
        limpa();
        System.out.print("Modelo do telemóvel: ");
        String novo_modelo = input_texto.nextLine();
        telemoveis[tel_ID][1] = novo_modelo;
        System.out.println("Modelo do telemovel editado com sucesso!\n");
    }

    public static void editarNifTel(int tel_ID){
        boolean found_nif = false, novo_nif_ok = false;
        int cliente_id = -99;

        //achando o nif do dono a partir do nº de tel
        String nif = telemoveis[tel_ID][2];
        for (int i = 0; i < num_clientes; i++){
            if (nif.equalsIgnoreCase(clientes[i][1])){
                cliente_id = i;
                break;
            }
        }

        System.out.print("NIF do novo dono do telemovel: ");
        String novo_nif = input_texto.nextLine();

        // trocar o nif do telemovel é igual a dar o mesmo a outra pessoa
        //Verificando se o novo NIF  pertence a cliente ja registrado
        for (int l = 0; l < num_clientes; l++){
            if (clientes[l][1].equalsIgnoreCase(novo_nif)){
                novo_nif_ok = true;

                telemoveis[tel_ID][2] = novo_nif;
                // adicionando um telemovel ao novo usuário
                int buffer = Integer.parseInt(clientes[l][3]) + 1;
                clientes[l][3] = String.valueOf(buffer);

                // descontando um telemovel do antigo usuário
                buffer = Integer.parseInt(clientes[cliente_id][3]) - 1;
                clientes[cliente_id][3] = String.valueOf(buffer);

                System.out.println("Novo NIF do telemovel editado com sucesso!");
                System.out.println("Agora o telemovel percente ao(a) ("+clientes[l][0]+").\n\n");
                tecleEnter();
                break;
            }
        }
        if (!novo_nif_ok){
            switchErros(3); //nif nao encontrado
        }
    }

    public static void apagarTelemovel(){ //pronta
        int tel_ID = -99999, client_id = -99;
        boolean found_number = false, found_nif = false;
        String novo_numero, modelo, nif = "placeholder";

        listarTelemoveisSimples();
        System.out.print("Qual o Nº do telemóvel que desejas apagar: ");
        String numero_tel = input_texto.nextLine();
        for (int i = 0; i < num_telemoveis; i++){
            if (telemoveis[i][0].equalsIgnoreCase(numero_tel)){
                tel_ID = i;
                found_number = true;
                break;
            }
        }
        if (!found_number){
            switchErros(5); //NUMERO DE TELEMOVEL N EXISTE
        } else {
            nif = telemoveis[tel_ID][2];
            for (int j = 0; j < num_clientes; j++){
                if (clientes[j][1].equalsIgnoreCase(nif)){
                    client_id = j;
                    found_nif = true;
                    break;
                }
            }
            if (found_nif){
                telemoveis[num_telemoveis][0] = "";
                telemoveis[num_telemoveis][1] = "";
                telemoveis[num_telemoveis][2] = "";
                for (int l = tel_ID; l < num_telemoveis; l++){
                    telemoveis[l][0] = telemoveis[l+1][0];
                    telemoveis[l][1] = telemoveis[l+1][1];
                    telemoveis[l][2] = telemoveis[l+1][2];
                }

                int buffer = Integer.parseInt(clientes[client_id][3]) - 1;
                clientes[client_id][3] = String.valueOf(buffer);
                num_telemoveis--;

                switchErros(1); // SUCESSO
            }
        }
    }

    // funcao auxiliar a funcao apagarCliente()
    public static void deletarTelemoveis(String nif){ //pronta
        for (int i = 0; i < num_telemoveis; i++){
            if (telemoveis[i][2].equalsIgnoreCase(nif)){
                telemoveis[num_telemoveis][0] = "";
                telemoveis[num_telemoveis][1] = "";
                telemoveis[num_telemoveis][2] = "";
                for (int l = i; l < num_telemoveis; l++){
                    telemoveis[l][0] = telemoveis[l+1][0];
                    telemoveis[l][1] = telemoveis[l+1][1];
                    telemoveis[l][2] = telemoveis[l+1][2];
                }
                num_telemoveis--;
            }
        }
    }
    public static void listarClientes(){
        boolean encerrado = false;
        int sessao = 5, localization = 0;
        int pagina_atual = 1;
        int total_paginas = Math.ceilDiv(num_clientes,5);
        String option;
        limpa();

        do {
            if (num_clientes < 6){
                limpa();
                System.out.println("----- Listagem de Clientes ("+pagina_atual+"/"+total_paginas+")-----\n");

                encerrado = true;
                for (int i = 0; i < num_clientes; i++){
                    listarUmCliente(i);
                }
                System.out.println("\n");
            }else{
                limpa();
                System.out.println("----- Listagem de Clientes ("+pagina_atual+"/"+total_paginas+")-----\n");

                for (int j = localization; j < sessao; j++){
                    if (j >= num_clientes){
                        encerrado = true;
                        break;
                    }else {
                        listarUmCliente(j);
                    }
                }
                pagina_atual++;
                sessao++;sessao++;sessao++;sessao++;sessao++;
                localization++;localization++;localization++;localization++;localization++;
            }
            if (encerrado) {
                break;
            }else {
                System.out.print("\nTecle enter para continuar ou 'x' para finalizar a busca: ");
                option = input_texto.nextLine();
                System.out.println("\n");
            }

        }while (!option.equalsIgnoreCase("x"));

        System.out.println("\nBusca finalizada.\n\n");
        tecleEnter();
    }

    public static void listarUmCliente(int i){
        System.out.println("("+i+") - Nome: ("+clientes[i][0]+ "), NIF: (" +clientes[i][1]+
                "), Morada: ("+clientes[i][2]+"), Qtd de telemoveis: ("+clientes[i][3]+").");
    }
    public static void listarTelemoveisSimples(){
        for (int i = 0; i < num_telemoveis; i++){
           listarUmTelemovel(i);
        }
        System.out.println("\n");
    }
    public static void listarTelemoveis(){
        boolean encerrado = false;
        int sessao = 5, localization = 0;
        String option;
        int pagina_atual = 1;
        int total_paginas = Math.ceilDiv(num_telemoveis,5);

        do {
            if (num_telemoveis < 6) {
                limpa();
                System.out.println("----- Listagem de Telemóveis ("+pagina_atual+"/"+total_paginas+")-----\n");
                encerrado = true;
                for (int i = 0; i < num_telemoveis; i++) {
                    listarUmTelemovel(i);
                }
                System.out.println("\n");
            }else{
                limpa();
                System.out.println("----- Listagem de Telemóveis ("+pagina_atual+"/"+total_paginas+")-----\n");
                for (int j = localization; j < sessao; j++){
                    if (j >= num_telemoveis){
                        encerrado = true;
                        break;
                    }else {
                        listarUmTelemovel(j);
                    }
                }
                pagina_atual++;
                sessao += 5;
                localization += 5;
            }
            if(encerrado) {
                break;
            }else {
                System.out.print("\nTecle enter para continuar ou 'x' para finalizar a busca: ");
                option = input_texto.nextLine();
                System.out.println("\n");
            }

        }while (!option.equalsIgnoreCase("x"));

        System.out.println("\nBusca finalizada.\n\n");
        tecleEnter();
    }

    public static void listarUmTelemovel(int i){
        System.out.println("("+i+") - Nª: (" +telemoveis[i][0]+ "), Modelo: (" +telemoveis[i][1]+
                "), NIF do dono: ("+telemoveis[i][2]+").");
    }

    public static void buscarCliente(){ //pronto
        int cliente_id = -99;
        boolean found_nif = false;
        System.out.print("\nNIF do dono do telemóvel: ");
        String nif = input_texto.nextLine();
        for (int j = 0; j < num_clientes; j++){
            if (clientes[j][1].equalsIgnoreCase(nif)){
                cliente_id = j;
                found_nif = true;
                break;
            }
        }
        if (found_nif){
            System.out.println();
            listarUmCliente(cliente_id);
            System.out.println();
            System.out.println("Telemovel(eis) registrado(s):");
            for (int i = 0; i < num_telemoveis; i++){
                if (telemoveis[i][2].equalsIgnoreCase(clientes[cliente_id][1])){
                    listarUmTelemovel(i);
                }
            }
            System.out.println("\n\n");
            tecleEnter();

        } else{
            switchErros(3); //erro, nif não existe
        }

    }
    public static int verificarNif(String nif){
        if ( num_clientes == 0){
            return 0;
        }
        else {
            for (int i = 0; i < num_clientes; i++){
                if (nif.equalsIgnoreCase(clientes[i][1])){
                    return i;
                }
            }
            //como eu sei que n há a posiçºao -1 de uma matriz, posso fazer um if == -1,
            // faça lago, casp >= 0, clitn_id = i
            return -1;
        }
    }

    public static void switchErros(int i){
        switch (i){
            case 1: //Sucesso
                System.out.println("\nSUCESSO!\n");
                tecleEnter();
                break;
            case 2: //erro, nif já existe
                System.out.println("\nErro!! Nif já resgitrado!");
                System.out.println("Operação cancelada\n");
                tecleEnter();
                break;
            case 3: //erro, nif não existe existe
                System.out.println("\nErro!! Nif não foi resgitrado!");
                System.out.println("Operação cancelada\n");
                tecleEnter();
                break;
            case 4: // erro Numero de Telemovel já Existe
                System.out.println("\nErro!! Nº de telemóvel já resgitrado!");
                System.out.println("Operação cancelada\n");
                tecleEnter();
                break;
            case 5: // erro Numero de Telemovel não Existe
                System.out.println("\nErro!! Nº de telemóvel não resgitrado!");
                System.out.println("Operação cancelada\n");
                tecleEnter();
                break;
            default:
                System.out.println("\nErro!");
                break;
        }
    }

    public static void limpa(){
        for (int i = 0; i < 25; i++){
            System.out.println();
        }
    }

    public static void tecleEnter(){ //funcao aguardar do java, igual o portugol
        System.out.println("Tecle a tecla Enter para continuar...\n");
        String enter = input_texto.nextLine();
    }

    public static void sair(){
        System.out.print("Certeza que queres sair? (sim/não): ");
        String resposta = input_texto.nextLine();
        if (resposta.equalsIgnoreCase("sim")){
            System.out.println("\n\nA sair...");
            desligar = true;
        }
    }

}