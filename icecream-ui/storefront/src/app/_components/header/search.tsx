import { BiSearchAlt } from 'react-icons/bi'
import { type FC } from 'react'
import { SearchIcon } from 'lucide-react'

import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select'
import { Input } from '@/components/ui/input'
import { Button } from '@/components/ui/button'
import type { Category } from '@/types'

type Props = {
  data: Category[]
}

export const Search: FC<Props> = ({ data }) => (
  <div className="flex w-full max-w-sm items-center space-x-2">
    <Select name="category">
      <SelectTrigger className="w-[180px]">
        <SelectValue placeholder="All" />
      </SelectTrigger>
      <SelectContent>
        <SelectItem key="All" value="all">
          All
        </SelectItem>
        {data.map((item) => (
          <SelectItem key={item.id} value={item.id}>
            {item.name}
          </SelectItem>
        ))}
      </SelectContent>
    </Select>
    <Input name="q" type="text" placeholder="Search..." className="md:w-[100px] lg:w-[300px]" />
    <Button>
      <SearchIcon />
    </Button>
  </div>
)
