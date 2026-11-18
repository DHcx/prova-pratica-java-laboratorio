# Respostas — Sistema de Reserva de Laboratorio

Nome completo: Diego Henrique Carlos      RA: 3111392523041

> Preencha todos os campos. Campo vazio é campo não avaliado.

---

# Etapa 1 — Diagnóstico

## Problema 1 — a conversão do inventario patrimonial

**Em que consiste o problema, e o que acontece quando o sistema cresce:**

A conversão da matriz de texto retornada pelo inventário para objetos `Equipamento` está em duplicidade no `CatalogoEquipamentos` e `RelatorioAcervo`. Cada classe implanta a conversão individualmente, as regras podem ficar diferentes, como ocorreu com a letra minúscula `d` do PAT-1003. Quando o sistema cresce ou o formato externo altera, é necessário corrigir vários lugares, mas isso aumenta o risco de divergências e de manutenções inconsistente.

**Princípio SOLID violado:**

Princípio da Responsabilidade Única (SRP) as classes que deveriam cuidar de suas funções de catálogo e relatório também assumem a responsabilidade de interpretar e converter o formato específico do inventário legado. E fica dependente direto da classe concreta, também contraria o Princípio da Inversão de Dependência (DIP).

**Classes afetadas (pelo nome):**

`CatalogoEquipamentos` e `RelatorioAcervo`.

**Que outra dependência indevida essas classes têm em relação ao
inventario patrimonial, e por que ela é um problema:**

Além de duplicar, as duas classes dependem diretamente da classe concreta `InventarioLegadoWS` e do seu formato matriz (`String[][]`), incluindo a posição de cada coluna. Isso cria acoplamento com um componente externo que é mantido por outra equipe. Se a implementação ou o formato do serviço mudar, as classes consumidoras também podem precisar ser alteradas. O certo é depender de uma abstração do próprio sistema.

## Problema 2 — o ciclo de vida de Reserva

**Em que consiste o problema, e o que acontece quando o sistema cresce:**

As regras de transição estão espalhadas nos métodos `retirar`, `devolver` e `cancelar`, usando uma `String` para representar a situação. Assim, a classe `Reserva` concentra as regras de todas as fases. Se surgir uma nova fase ou uma nova regra, os métodos existentes precisam ser modificados, aumentando a complexidade e o risco de alterar uma transição que já esta funcionando.

**Princípio SOLID violado:**

Princípio Aberto/Fechado (OCP), porque a classe tem de ser modificada para acrescentar ou alterar comportamentos específicos de cada estado, em vez de permitir essa evolução por meio de novas classes de estado.

**Classes afetadas (pelo nome):**

`Reserva`.

---

### Etapa 3 — Padrão comportamental

package siga;

/** Mantém os dados da reserva e delega as operações ao estado atual. */
public class Reserva {

    private final Usuario solicitante;
    private final Equipamento item;
    private EstadoReserva estado;

    public Reserva(Usuario solicitante, Equipamento item) {
        this.solicitante = solicitante;
        this.item = item;
        this.estado = new Solicitada();
    }

    public String getSituacao() {
        return estado.getSituacao();
    }

    public void retirar() {
        estado.retirar(this);
    }

    public void devolver() {
        estado.devolver(this);
    }

    public void cancelar() {
        estado.cancelar(this);
    }

    void alterarEstado(EstadoReserva novoEstado) {
        this.estado = novoEstado;
    }

    public Usuario getSolicitante() {
        return solicitante;
    }

    public Equipamento getEquipamento() {
        return item;
    }
}


# Etapa 4 — Justificativa

## Padrão aplicado ao Problema 1

**O problema que ele resolve (descreva antes de nomear o padrão):**

O sistema recebe equipamentos em formato externo que não corresponde ao formato de objetos utilizado internamente. A conversão estava repetida em duas classes e as duas conheciam diretamente o serviço legado. A solução cria uma classe intermediária que interpreta o formato externo uma única vez e oferece os equipamentos por meio de uma interface conhecida pelo sistema.

**Nome do padrão:**

Adapter (Adaptador).

**Alternativa descartada e por que ela não resolveria por completo:**

Alterar o próprio `InventarioLegadoWS` foi descartado porque ele é um serviço externo mantido por outra equipe e o enunciado proíbe sua alteração. Além disso, modificar o serviço não resolveria, por si só, o acoplamento das classes consumidoras à classe concreta. O Adapter preserva o serviço e isola seu formato.

## Padrão aplicado ao Problema 2

**O problema que ele resolve (descreva antes de nomear o padrão):**

Cada fase da reserva tem operações permitidas e proibidas e define para qual fase a reserva pode avançar. Antes, essas regras estavam espalhadas em condicionais na classe `Reserva`. A solução coloca as regras em classes que representam cada fase, deixando a reserva delegar as operações ao estado atual.

**Nome do padrão:**

State (Estado).

**Alternativa descartada e por que ela não resolveria por completo:**

Trocar a `String` por um `enum` melhoraria a segurança dos valores e evitaria erros de digitação, mas não retiraria as condições dos métodos de `Reserva` nem colocaria o comportamento de cada fase em um único lugar. O State organiza tanto as operações permitidas quanto as transições entre fases.
