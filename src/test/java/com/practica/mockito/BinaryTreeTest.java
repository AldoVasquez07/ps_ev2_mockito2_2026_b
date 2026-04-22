package com.practica.mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BinaryTreeTest {

    private BinaryTree tree;

    @BeforeEach
    void setUp() {
        tree = new BinaryTree();
    }

    @Test
    void testPutRootNode() {
        tree.put(10);
        assertNotNull(tree.getRoot());
        assertEquals(10, tree.getRoot().data);
    }

    @Test
    void testFindValueExisting() {
        tree.put(20);
        tree.put(10);
        tree.put(30);

        BinaryTree.Node result = tree.find(10);
        assertNotNull(result);
        assertEquals(10, result.data);
    }

    @Test
    void testRemoveLeafNode() {
        tree.put(50);
        tree.put(25);
        
        boolean removed = tree.remove(25);
        
        assertTrue(removed);
        assertNull(tree.find(25).left);
    }


    @Test
    void testCustomRootWithMock(@Mock BinaryTree.Node mockNode) {
        mockNode.data = 100; 
        BinaryTree customTree = new BinaryTree(mockNode);
        assertEquals(100, customTree.getRoot().data);
        verifyNoInteractions(mockNode);
    }

    @Test
    void testFindSuccessorLogic() {
        tree.put(15);
        tree.put(10);
        tree.put(20);
        tree.put(18);
        tree.put(25);

        BinaryTree.Node node15 = tree.find(15);
        BinaryTree.Node successor = tree.findSuccessor(node15);

        assertEquals(18, successor.data);
    }
}