<template>
    <div id="table">
      <Toast position="top-left" group="tl" />
        <div class="table_container">
          <Button @click="change" icon="pi pi-table" rounded class="table_button"/>
          <div v-if="isFirstVisible">
            <div class="input_container">
              <span class="p-float-label container">
                <InputNumber class="input" id="x" v-model="x_value" :min="-5" :max="5" :minFractionDigits="0" :maxFractionDigits="5"/>
                <label class="input_text" for="x">value X (-5..5)</label>
              </span>
              <span class="p-float-label container">
                <InputNumber class="input" id="y" v-model="y_value" :min="-5" :max="3" :minFractionDigits="0" :maxFractionDigits="5"/>
                <label class="input_text" for="y">value Y (-5..3)</label>
              </span>
              <span class="p-float-label container">
                <InputNumber class="input" id="r" v-model="r_value" :min="0.1" :max="5" :minFractionDigits="0" :maxFractionDigits="5"/>
                <label class="input_text" for="r">Radius (0..5)</label>
              </span>
            </div>
            <div class="button_container">
              <div style="position: relative; display: inline-block">
                <img src="../assets/zadanie.png" alt="graphic" style="width: 100%; height: auto; display: block; cursor: pointer" @click="imageClick" ref="image"/>
                <div id="dot" :style="{ top: dotTop + 'px', left: dotLeft + 'px', visibility: dotVisibility, background: dotColor }" class="table_point"></div>
              </div>
              <Button @click="addPoint" class="submit_button" label="Submit" icon="pi pi-check" />
              <Button @click="clearPoints" class="submit_button" label="Clear" icon="pi pi-check" />
            </div>
          </div>
          <div v-else class="table_main">
            <DataTable :value="this.$store.getters.getPoints" showGridlines :size="'small'" tableStyle="max-width: 90%">
              <Column field="x" header="X"></Column>
              <Column field="y" header="Y"></Column>
              <Column field="r" header="R"></Column>
              <Column field="hit" header="Hit">
                <template #body="point">
                  <Tag :value="getHitStatus(point)" :severity="getHitColor(point)"/>
                </template>
              </Column>
            </DataTable>
          </div>
        </div>
    </div>
</template>

<script>
import { AXIOS } from '@/http/http-common'
export default {
  name: 'TableView',
  data () {
    return {
      dotTop: 101,
      dotLeft: 101,
      dotVisibility: 'hidden',
      dotColor: 'lightgreen',
      x_value: null,
      y_value: null,
      r_value: null,
      isFirstVisible: true
    }
  },
  methods: {
    change () {
      this.isFirstVisible = !this.isFirstVisible
    },
    getHitStatus (point) {
      if (point.data.hit) {
        return 'Попадание'
      } else {
        return 'Промах'
      }
    },
    getHitColor (point) {
      if (point.data.hit) {
        return 'success'
      } else {
        return 'danger'
      }
    },
    async addPoint () {
      const data = {
        x: parseFloat(this.x_value), y: parseFloat(this.y_value), r: parseFloat(this.r_value), username: this.$store.getters.getUsername
      }
      const headers = {
        headers: {
          Authorization: 'Basic ' + btoa(this.$store.getters.getUsername + ':' + this.$store.getters.getPassword)
        }
      }
      AXIOS.post('point/add_point', data, headers)
        .then(response => {
          const newPoint = {
            x: response.data.x,
            y: response.data.y,
            r: response.data.r,
            hit: response.data.hit
          }
          this.$store.dispatch('add', newPoint)
          this.dotTop = 101 - newPoint.y / newPoint.r * 80
          this.dotLeft = 101 + newPoint.x / newPoint.r * 80
          if (newPoint.hit) {
            this.dotColor = 'lightgreen'
          } else {
            this.dotColor = 'red'
          }
          this.dotVisibility = 'visible'
          this.$toast.add({ severity: 'success', summary: 'Point hitted', detail: 'Check table', group: 'tl', life: 3000 })
        })
        .catch(error => {
          if (error.response.status === 401) {
            this.$toast.add({ severity: 'error', summary: 'Conflict', detail: 'Bad data', group: 'tl', life: 3000 })
          } else {
            this.$toast.add({ severity: 'error', summary: 'Conflict', detail: 'Due to ' + error.message, group: 'tl', life: 3000 })
          }
        })
    },
    async clearPoints () {
      AXIOS.get('point/clear_points', {
        params: {
          username: this.$store.getters.getUsername
        },
        headers: {
          Authorization: 'Basic ' + btoa(this.$store.getters.getUsername + ':' + this.$store.getters.getPassword)
        }
      })
        .then(response => {
          this.$store.dispatch('clear')
          this.$toast.add({ severity: 'success', summary: 'Cleared', detail: 'Table empty', group: 'tl', life: 3000 })
        })
    },
    imageClick (event) {
      if (this.r_value) {
        const center = 105
        const image = this.$refs.image
        const imageRect = image.getBoundingClientRect()

        this.x_value = ((event.clientX - imageRect.left - center) / (80 / this.r_value)).toFixed(5)
        this.y_value = ((imageRect.top - event.clientY + center) / (80 / this.r_value)).toFixed(5)
        this.addPoint()
      } else {
        this.$toast.add({ severity: 'error', summary: 'Error', detail: 'Radius must be selected', group: 'tl', life: 3000 })
      }
    }
  },
  created () {
    this.$store.dispatch('loadPoints')
  }
}
</script>

<style scoped>
.table_container {
  position: relative;
  border-radius: 2rem;
  padding-bottom: 50px;
  width: 100%;
  margin-top: 100px;
  background-image: linear-gradient(to right, var(--bluegray-500), var(--bluegray-800));
}
.input_text {
  color: black;
  text-align: center;
}
.input_container {
  display: flex;
  justify-content: space-around;
  margin-left: 100px;
}
.input {
  width: 50px;
}
.container {
  flex: 1;
  margin-left: 3px;
  margin-right: 10px;
  margin-top: 75px;
}
.button_container {
  margin-top: 50px;
  display: flex;
  justify-content: space-around;
}
.submit_button {
  height: 50px;
  margin-top: 80px;
  margin-left: -200px;
}
.table_button {
  position: absolute;
  top: 15px;
  right: 15px;
}
.table_main {
  padding-left: 60px;
  padding-top: 75px;
}
.table_point {
  position: absolute;
  width: 10px;
  height: 10px;
  border-radius: 50%;
}
</style>
