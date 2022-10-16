package com.codely.jpakotlin.application.repository

import com.codely.jpakotlin.domain.Forest
import com.codely.jpakotlin.domain.Tree
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ForestRepositoryTest {

    @Autowired
    private lateinit var forestRepository: ForestRepository

    @Autowired
    private lateinit var treeRepository: TreeRepository

    @BeforeEach
    fun setUp() {
        forestRepository.deleteAll();
        treeRepository.deleteAll();
    }

    @Test
    fun saveOneElementTest() {
        val tree = Tree("oak")
        treeRepository.save(tree)
        val forest = Forest(
            id = 1,
            name = "small forest",
            trees = mutableSetOf(tree)
        )
        forestRepository.save(forest)
        val forestsInDb = forestRepository.findAll()
        val treesInDb = treeRepository.findAll()

        assertThat(forestsInDb).isNotEmpty
        assertThat(forestsInDb)
            .flatExtracting("trees")
            .extracting("name")
            .contains("oak")
        assertThat(treesInDb).isNotEmpty
        assertThat(treesInDb).flatExtracting("forests")
            .extracting("name")
            .contains("small forest")
    }

    @Test
    fun saveManyElementTest() {
        val oak = Tree("oak")
        val palm = Tree("palm")
        val olive = Tree("olive")
        treeRepository.saveAll(listOf(oak, palm, olive))
        val smallForest = Forest(
            id = 1,
            name = "small forest",
            trees = mutableSetOf(oak)
        )
        val mediumForest = Forest(
            id = 2,
            name = "medium forest",
            trees = mutableSetOf(oak, olive)
        )
        val bigForest = Forest(
            id = 3,
            name = "big forest",
            trees = mutableSetOf(oak, olive, palm)
        )
        forestRepository.saveAll(listOf(smallForest, mediumForest, bigForest))
        assertThat(forestRepository.findAll()).hasSize(3)
        assertThat(treeRepository.findAll()).hasSize(3)
        assertThat(forestRepository.findById(1).get().trees)
            .extracting("name").contains("oak")
        assertThat(forestRepository.findById(2).get().trees)
            .extracting("name").containsExactlyInAnyOrder("oak", "olive")
        assertThat(forestRepository.findById(3).get().trees)
            .extracting("name").containsExactlyInAnyOrder("oak", "olive", "palm")
        assertThat(treeRepository.findById("oak").get().forests)
            .extracting("id").containsExactlyInAnyOrder(1, 2, 3)
        assertThat(treeRepository.findById("palm").get().forests)
            .extracting("id").containsExactlyInAnyOrder(3)
        assertThat(treeRepository.findById("olive").get().forests)
            .extracting("id").containsExactlyInAnyOrder(2, 3)
    }

    @Test
    fun findAllByTreesTest() {
        val oak = Tree("oak")
        val palm = Tree("palm")
        val olive = Tree("olive")
        treeRepository.saveAll(listOf(oak, palm, olive))
        val smallForest = Forest(
            id = 1,
            name = "small forest",
            trees = mutableSetOf(oak)
        )
        val mediumForest = Forest(
            id = 2,
            name = "medium forest",
            trees = mutableSetOf(oak, olive)
        )
        val bigForest = Forest(
            id = 3,
            name = "big forest",
            trees = mutableSetOf(oak, olive, palm)
        )
        forestRepository.saveAll(listOf(smallForest, mediumForest, bigForest))
        val forestsWithOlives = forestRepository.findAllByTrees(palm)
        assertThat(forestsWithOlives).hasSize(1)
        assertThat(forestsWithOlives).extracting("id")
            .containsExactlyInAnyOrder(3)
    }

    @Test
    fun findAllByTreesNameTest() {
        val oak = Tree("oak")
        val palm = Tree("palm")
        val olive = Tree("olive")
        treeRepository.saveAll(listOf(oak, palm, olive))
        val smallForest = Forest(
            id = 1,
            name = "small forest",
            trees = mutableSetOf(oak)
        )
        val mediumForest = Forest(
            id = 2,
            name = "medium forest",
            trees = mutableSetOf(oak, olive)
        )
        val bigForest = Forest(
            id = 3,
            name = "big forest",
            trees = mutableSetOf(oak, olive, palm)
        )
        forestRepository.saveAll(listOf(smallForest, mediumForest, bigForest))
        val forestsWithOlives = forestRepository.findAllByTreesName("olive")
        assertThat(forestsWithOlives).hasSize(2)
        assertThat(forestsWithOlives).extracting("id")
            .containsExactlyInAnyOrder(2, 3)
    }

    @Test
    fun findAllByTreesInTest() {
        val oak = Tree("oak")
        val palm = Tree("palm")
        val olive = Tree("olive")
        val smallForest = Forest(
            id = 1,
            name = "small forest",
            trees = mutableSetOf(oak)
        )
        val mediumForest = Forest(
            id = 2,
            name = "medium forest",
            trees = mutableSetOf(oak, olive)
        )
        val bigForest = Forest(
            id = 3,
            name = "big forest",
            trees = mutableSetOf(oak, olive, palm)
        )
        forestRepository.saveAllAndFlush(listOf(smallForest, mediumForest, bigForest))
        val forestsWithOlivesOrOaks =
            forestRepository.findDistinctByTreesIn(setOf(oak, olive))
        assertThat(forestsWithOlivesOrOaks).hasSize(3)
        assertThat(forestsWithOlivesOrOaks).extracting("id")
            .containsExactlyInAnyOrder(1, 2, 3)
    }

    @Test
    fun findAllByForestIdTest() {
        val oak = Tree("oak")
        val palm = Tree("palm")
        val olive = Tree("olive")
        treeRepository.saveAll(listOf(oak, palm, olive))
        val smallForest = Forest(
            id = 1,
            name = "small forest",
            trees = mutableSetOf(oak)
        )
        val mediumForest = Forest(
            id = 2,
            name = "medium forest",
            trees = mutableSetOf(oak, olive)
        )
        val bigForest = Forest(
            id = 3,
            name = "big forest",
            trees = mutableSetOf(oak, olive, palm)
        )
        forestRepository.saveAll(listOf(smallForest, mediumForest, bigForest))
        val treesInForest2 = treeRepository.findAllByForestsId(2)
        assertThat(treesInForest2).hasSize(2)
        assertThat(treesInForest2).extracting("name")
            .containsExactlyInAnyOrder("oak", "olive")
    }

    @Test
    fun findAllByForestIdInTest() {
        val oak = Tree("oak")
        val palm = Tree("palm")
        val olive = Tree("olive")
        treeRepository.saveAll(listOf(oak, palm, olive))
        val smallForest = Forest(
            id = 1,
            name = "small forest",
            trees = mutableSetOf(oak)
        )
        val mediumForest = Forest(
            id = 2,
            name = "medium forest",
            trees = mutableSetOf(oak, olive)
        )
        val bigForest = Forest(
            id = 3,
            name = "big forest",
            trees = mutableSetOf(oak, olive, palm)
        )
        forestRepository.saveAll(listOf(smallForest, mediumForest, bigForest))
        val treesInForests =
            treeRepository.findDistinctByForestsIdIn(setOf(1, 3))
        assertThat(treesInForests).hasSize(3)
        assertThat(treesInForests).extracting("name")
            .containsExactlyInAnyOrder("oak", "olive", "palm")
    }

}
