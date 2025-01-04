import { type FC } from "react"

type Props = {
  params: {
    id: string
  }
}

const Page: FC<Props> = ({ params }) => (
  <div>{params.id}</div>
)

export default Page
