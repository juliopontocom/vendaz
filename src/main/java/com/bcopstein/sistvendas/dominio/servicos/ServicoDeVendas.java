package com.bcopstein.sistvendas.dominio.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.dominio.modelos.PedidoModel;
import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;
import com.bcopstein.sistvendas.dominio.persistencia.IEstoqueRepositorio;
import com.bcopstein.sistvendas.dominio.persistencia.IOrcamentoRepositorio;

@Service
public class ServicoDeVendas {
    private IOrcamentoRepositorio orcamentos;
    private IEstoqueRepositorio estoque;

    @Autowired
    public ServicoDeVendas(IOrcamentoRepositorio orcamentos,IEstoqueRepositorio estoque){
        this.orcamentos = orcamentos;
        this.estoque = estoque;
    }
    
    public List<ProdutoModel> produtosDisponiveis() {
        return estoque.todosComEstoque();
    }

    public OrcamentoModel recuperaOrcamentoPorId(long id) {
        return this.orcamentos.recuperaPorId(id);
    }

    public OrcamentoModel criaOrcamento(PedidoModel pedido) {
        var novoOrcamento = new OrcamentoModel();
        novoOrcamento.addItensPedido(pedido);
        double custoItens = novoOrcamento.getItens().stream()
            .mapToDouble(it->it.getProduto().getPrecoUnitario()*it.getQuantidade())
            .sum();
        novoOrcamento.setImposto(custoItens * 0.1);
        if (novoOrcamento.getItens().size() > 5){
                novoOrcamento.setDesconto(custoItens * 0.05);
            }else{
                novoOrcamento.setDesconto(0.0);
            }
        novoOrcamento.setCustoConsumidor(custoItens + novoOrcamento.getImposto() - novoOrcamento.getDesconto());
        return this.orcamentos.cadastra(novoOrcamento);
    }
 
    public OrcamentoModel efetivaOrcamento(long id) {
        // Recupera o orçamento
    
        // Verifica se tem quantidade em estoque para todos os itens

        // Se tem quantidade para todos os itens, da baixa no estoque para todos

        // Marca o orcamento como efetivado

        // Retorna o orçamento marcado como efetivado ou não conforme disponibilidade do estoque
        return null;
    }
}
